package com.bmw.fd.spring.api.doc;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.parser.core.models.ParseOptions;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

@Configuration
@ConditionalOnResource(resources = "classpath:apidoc/index.yaml")
public class ApidocConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApidocConfiguration.class);

    @Bean
    public ApidocController apidocController() throws IOException {
        return new ApidocController(openAPI());
    }

    @Bean
    public OpenAPI openAPI() throws IOException {
        final Path apidoc = copyApidocIntoTempDir();
        final OpenAPIParser openAPIParser = new OpenAPIParser();
        final ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolve(true);
        OpenAPI openAPI = openAPIParser.readLocation(apidoc.resolve("index.yaml").toString(), null, parseOptions).getOpenAPI();
        //FileUtils.deleteDirectory(apidoc.toFile());   // throws an error on windows. Since it is a temporary folder, it should be deleted automatically
        fixNullScopes(openAPI);
        return openAPI;
    }

    /**
     * OpenAPIParser supports reading urls and files - it does not support reading from classpath
     * so we copy all yaml on classpath in temp directory
     */
    private Path copyApidocIntoTempDir() throws IOException {
        final Path apidoc = Files.createTempDirectory("apidoc");
        PathMatchingResourcePatternResolver resolver =  new PathMatchingResourcePatternResolver();
        for (var resource : resolver.getResources("classpath*:apidoc/*.yaml")) {
            LOGGER.info("Apidoc found: " + resource.getFilename());
            Files.copy(resource.getInputStream(), apidoc.resolve(resource.getFilename()));
        };
        return apidoc;
    }

    private void fixNullScopes(OpenAPI openAPI) {
        // this is a small bug in OpenAPI parser/serializer

        // components:
        //  securitySchemes:
        //    oauth2sample:
        //      type: oauth2
        //      flows: authorizationCode:
        //          scopes: # <--- this is mandatory
        //
        // For apis that don't use scopes, scopes should be set to {}. The parser will read this as null, resulting
        // in scopes not being including at all in the api definition - which makes the api definition invalid.
        // Our workaround is to search for null scopes and replace them with empty scopes.
        if (openAPI != null && openAPI.getComponents() != null && openAPI.getComponents().getSecuritySchemes() != null) {
            openAPI.getComponents().getSecuritySchemes().values().stream()
                    .map(SecurityScheme::getFlows)
                    .filter(Objects::nonNull)
                    .flatMap(f -> Stream.of(f.getAuthorizationCode(), f.getClientCredentials(), f.getImplicit(), f.getPassword()))
                    .filter(Objects::nonNull)
                    .forEach(flow -> {
                        if (flow.getScopes() == null) {
                            flow.setScopes(new Scopes());
                        }
                    });
        }
    }
}
