package com.bmw.fd.spring.api.doc;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.parser.ObjectMapperFactory;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping("/apidoc")
public class ApidocController {

    private final OpenAPI api;

    private final ObjectMapper yamlMapper;
    private final ObjectMapper jsonMapper;


    public ApidocController(OpenAPI api)  {
        this.api = api;
        this.yamlMapper = ObjectMapperFactory.createYaml();
        this.jsonMapper = ObjectMapperFactory.createJson();
        // configuration for enums is wrong per default - this is a bug in ObjectMapperFactory and should be fixed there
        this.yamlMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        this.jsonMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
    }


    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public void swaggerUI(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        IOUtils.copy(getClass().getResourceAsStream("/apidoc/index.html"), response.getOutputStream());
    }

    // As soon as they are finalized by openapi set correct content types for yaml and json specs
    // see https://github.com/OAI/OpenAPI-Specification/issues/110
    // will probably be
    // application/vnd.oai.openapi (YAML variant)
    // application/vnd.oai.openapi+json (JSON only variant)

    @GetMapping("index.yaml")
    public void getYaml(HttpServletResponse response) throws IOException {
        ObjectNode tree = yamlMapper.valueToTree(api);
        removeEmptyExtensions(tree);
        yamlMapper.writeTree(yamlMapper.getFactory().createGenerator(response.getOutputStream()), tree);
    }

    @GetMapping(value = "index.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public void getJson(HttpServletResponse response) throws IOException {
        ObjectNode tree = jsonMapper.valueToTree(api);
        removeEmptyExtensions(tree);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        jsonMapper.writeTree(jsonMapper.getFactory().createGenerator(response.getOutputStream()), tree);
    }

    /**
     * Another small bug in OpenAPI parser/serializer - Empty extensions will be serialized as
     * extensions: {}
     * Some tools will not regard this as valid - so we remove them.
     */
    private void removeEmptyExtensions(ObjectNode tree) {
        if (tree.get("components") instanceof ObjectNode) {
            ObjectNode components = (ObjectNode) tree.get("components");
            JsonNode extensions = components.get("extensions");
            if (extensions != null && extensions.size() == 0) {
                components.remove("extensions");
            }
        }
    }
}
