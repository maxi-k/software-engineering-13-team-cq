package com.bmw.fd.apimock.boundary.util;

import com.bmw.fd.spring.api.CollectionResource;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.models.media.Schema;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RestMock {

    private final Path dataDir;
    private final JsonStore store;
    private final Function<ObjectNode, ObjectNode> responseFilter;

    private RestMock(Path dataDir, JsonStore store, Function<ObjectNode, ObjectNode> responseFilter) {
        this.dataDir = dataDir;
        this.store = store;
        this.responseFilter = responseFilter;
    }

    public static Builder create(String dataDir) {
        return new Builder(dataDir);
    }

    public JsonStore store() {
        return store;
    }

    public ResponseEntity<ObjectNode> get(String id) {
        return store.get(id)
                .map(responseFilter)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<ObjectNode> getFirst(Predicate<ObjectNode> test) {
        return store.findFirst(test)
                .map(responseFilter)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<CollectionResource<ObjectNode>> getAll() {
        return get(x -> true);
    }

    public ResponseEntity<CollectionResource<ObjectNode>> get(Predicate<ObjectNode> test) {
        return ResponseEntity.ok(new CollectionResource<>(
                store
                        .find(test).stream()
                        .map(responseFilter)
                        .collect(Collectors.toList())));
    }

    public void deliverFile(String fileName, HttpServletResponse response) throws IOException {
        Path imgFile = dataDir.resolve(fileName);
        if (Files.isRegularFile(imgFile)) {
            try (InputStream inputStream = Files.newInputStream(imgFile)) {
                StreamUtils.copy(inputStream, response.getOutputStream());
                new Magic();
                response.setContentType(Magic.getMagicMatch(imgFile.toFile(), true).getMimeType());
            } catch (MagicException | MagicParseException | MagicMatchNotFoundException e) {
                throw new IOException(e);
            }
        } else {
            response.setStatus(404);
        }
    }

    public ResponseEntity<ObjectNode> post(ObjectNode newItem) {
        ObjectNode saved = store.add(newItem);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(store.getId(saved)).toUri();
        return ResponseEntity
                .created(location)
                .body(responseFilter.apply(saved));
    }

    public ResponseEntity<ObjectNode> put(String id, ObjectNode item) {
        return store.get(id)
                .map(i -> store.add(item))
                .map(responseFilter)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<ObjectNode> delete(String id) {
        return store.remove(id)
                .map(responseFilter)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public static class Builder {

        private final Path dataDir;
        private String idField = "id";
        private Supplier<String> idGenerator;
        private Function<ObjectNode, ObjectNode> persistFilter = Function.identity();
        private Function<ObjectNode, ObjectNode> responseFilter = Function.identity();
        private Schema schema;
        private Set<String> internalFields = new HashSet<>();

        private Builder(String dataDir) {
            this.dataDir = Paths.get(dataDir);
        }

        public Builder withResponseFilter(Function<ObjectNode, ObjectNode> responseFilter) {
            this.responseFilter = responseFilter;
            return this;
        }

        public Builder withIdField(String idField) {
            this.idField = idField;
            return this;
        }

        public Builder withUUIDGenerator() {
            idGenerator = () -> UUID.randomUUID().toString();
            return this;
        }

        public Builder withSchema(Schema schema) {
            this.schema = schema;
            return this;
        }

        public Builder withInternalFields(String... internalFields) {
            return withInternalFields(Arrays.asList(internalFields));
        }

        public Builder withInternalFields(Iterable<String> internalFields) {
            this.internalFields = new HashSet<>();
            internalFields.forEach(this.internalFields::add);
            return this;
        }

        public RestMock build() {
            return new RestMock(dataDir, new JsonStore(dataDir, idField,
                    persistFilter.andThen(this::generateId).andThen(schemaFilter())),
                    responseFilter.andThen(this::removeInternalFields));
        }

        private Function<ObjectNode, ObjectNode> schemaFilter() {
            if (schema == null) {
                return Function.identity();
            }
            var schemaFilter = new SchemaFilter(schema);
            return node -> {
                var internalFieldValues = internalFields.stream().collect(Collectors.toMap(Function.identity(), node::get));
                var filtered = schemaFilter.apply(node);
                internalFieldValues.forEach(filtered::replace);
                return filtered;
            };
        }

        private ObjectNode generateId(ObjectNode node) {
            if (idGenerator == null) {
                return node;
            }
            // TODO: don't accept empty string in idField
            if (!node.hasNonNull(idField) || node.get(idField).textValue().isEmpty()) {
                node.put(idField, idGenerator.get());
            }
            return node;
        }

        private ObjectNode removeInternalFields(ObjectNode node) {
            return node.deepCopy().remove(internalFields);
        }
    }
}
