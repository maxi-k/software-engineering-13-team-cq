package com.bmw.fd.apimock.boundary.validation;

import com.atlassian.oai.validator.interaction.ApiOperationResolver;
import com.atlassian.oai.validator.model.ApiOperationMatch;
import com.atlassian.oai.validator.model.Request;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Service
public class OpenApiSchemaLocator {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiSchemaLocator.class);

    private final Map<String, OpenAPI> apis;

    public OpenApiSchemaLocator() {
        this.apis = Stream.of("apidoc/index.yaml").collect(Collectors.toMap(Function.identity(), OpenApiSchemaLocator::loadApi));
    }

    public Schema locateSchema(String path) {
        return locateSchema(path, Request.Method.GET, "200");
    }

    public Schema locateSchema(String path, Request.Method method, String response) {
        for (OpenAPI api : apis.values()) {
            ApiOperationResolver resolver = new ApiOperationResolver(api, "/api");
            ApiOperationMatch apiOperation = resolver.findApiOperation(path, method);
            if (apiOperation.isPathFound()) {
                return resolveRefs(apiOperation.getApiOperation().getOperation().getResponses().get(response).getContent().get("application/json").getSchema(), api);
            }
        }
        return null;
    }

    private Schema resolveRefs(Schema schema, OpenAPI api) {
        if (schema.get$ref() != null && schema.get$ref().startsWith("#/components/schemas/")) {
            return resolveRefs(api.getComponents().getSchemas().get(schema.get$ref().substring("#/components/schemas/".length())), api);
        }
        Map<String, Schema> properties = schema.getProperties();
        if (properties != null) {
            schema.setProperties(properties.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            e -> resolveRefs(e.getValue(), api))));
        }
        return schema;
    }

    private static OpenAPI loadApi(String url) {
        final OpenAPIParser openAPIParser = new OpenAPIParser();
        final ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolve(true);
        SwaggerParseResult parseResult = openAPIParser.readLocation(url, null, parseOptions);
        LOGGER.info("Parsed openapi schema at {}", url);
        return parseResult.getOpenAPI();
    }
}
