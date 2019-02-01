package com.bmw.fd.apimock.boundary.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Lists;
import org.apache.commons.lang.math.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonStore.class);

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private final Map<String, ObjectNode> data = new ConcurrentHashMap<>();
    private final Path dataDir;
    private final String idField;
    private final Function<ObjectNode, ObjectNode> persistFilter;
    private final Map<String, Instant> lastModified = new HashMap<>();
    private final Map<String, Set<String>> loadedIds = new HashMap<>();

    public JsonStore(String dataDir, String idField) {
        this(Paths.get(dataDir), idField, new IdGenerator(idField));
    }

    public JsonStore(Path dataDir, String idField, Function<ObjectNode, ObjectNode> persistFilter) {
        this.dataDir = dataDir;
        this.idField = idField;
        this.persistFilter = persistFilter;

        LOGGER.info("Scanning for test data at: {}", dataDir.toAbsolutePath());
        reloadTestData();
    }

    public Optional<ObjectNode> get(String id) {
        reloadTestData();
        return Optional.ofNullable(data.get(id));
    }

    public List<ObjectNode> getAll() {
        reloadTestData();
        return new ArrayList<>(data.values());
    }

    public Optional<ObjectNode> findFirst(Predicate<ObjectNode> test) {
        return stream().filter(test).findFirst();
    }

    public List<ObjectNode> find(Predicate<ObjectNode> test) {
        return stream().filter(test).collect(Collectors.toList());
    }

    private Stream<ObjectNode> stream() {
        reloadTestData();
        return data.values().stream();
    }

    public String getId(ObjectNode item) {
        return item.get(idField).textValue();
    }

    public ObjectNode add(ObjectNode item) {
        ObjectNode filtered = persistFilter.apply(item);
        data.put(getId(filtered), filtered);
        return filtered;
    }

    public Optional<ObjectNode> remove(Object key) {
        return Optional.ofNullable(data.remove(key));
    }

    private synchronized void reloadTestData() {
        try {
            Files.list(dataDir)
                    .filter(Files::isRegularFile)
                    .filter(f -> f.getFileName().toString().endsWith(".yaml") || f.getFileName().toString().endsWith(".yaml.tpl"))
                    .forEach(file -> {
                        try {
                            String fileName = file.getFileName().toString();
                            Instant fileModifiedTime = Files.getLastModifiedTime(file).toInstant();
                            if (!lastModified.containsKey(fileName) || lastModified.get(fileName).isBefore(fileModifiedTime)) {
                                lastModified.put(fileName, fileModifiedTime);
                                loadTestData(file);
                            }
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void loadTestData(Path file) {
        String fileName = file.getFileName().toString();
        loadedIds.getOrDefault(fileName, Collections.emptySet()).forEach(this::remove);
        loadedIds.remove(fileName);

        try {
            if (fileName.endsWith(".yaml.tpl")) {
                ExpressionParser parser = new SpelExpressionParser();
                String template = new String(Files.readAllBytes(file), "UTF-8");
                Expression expression = parser.parseExpression(template, new TemplateParserContext());
                ContextRoot root = new ContextRoot();

                ObjectNode tpl = generateJson(expression, root, 0);
                if (tpl.has("_times_")) {
                    for (int index = 0; index < tpl.get("_times_").asInt(); index++) {
                        ObjectNode json = generateJson(expression, root, index);
                        json.remove("_times_");
                        add(fileName, json);
                    }
                } else {
                    add(fileName, tpl);
                }
            } else {
                add(fileName, (ObjectNode) mapper.readTree(new FileInputStream(file.toFile())));
            }
            LOGGER.info("(Re)load test data: {}", file);
        } catch (Exception e) {
            LOGGER.error("Error (re)loading test data: {}", file, e);
        }
    }

    private void add(String fileName, ObjectNode item) {
        String addedId = getId(add(item));
        loadedIds.putIfAbsent(fileName, new HashSet<>());
        loadedIds.get(fileName).add(addedId);
    }

    private ObjectNode generateJson(Expression expression, ContextRoot root, int index) throws IOException {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("i", index);
        context.setRootObject(root);

        String renderedTemplate = expression.getValue(context, String.class);
        return (ObjectNode) mapper.readTree(renderedTemplate);
    }

    private static class ContextRoot {
        private final Random random = new Random();

        public String format(String format, Object... args) {
            return String.format(format, args);
        }

        public int random(int min, int max) {
            return random.nextInt(max - min) + min;
        }
    }

}
