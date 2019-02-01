package com.bmw.fd.apimock.boundary.validation;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.interaction.ApiOperationResolver;
import com.atlassian.oai.validator.model.ApiOperationMatch;
import com.atlassian.oai.validator.model.Request;
import com.atlassian.oai.validator.springmvc.OpenApiValidationFilter;
import com.atlassian.oai.validator.springmvc.OpenApiValidationInterceptor;
import com.atlassian.oai.validator.whitelist.ValidationErrorsWhitelist;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.atlassian.oai.validator.whitelist.rule.WhitelistRules.messageHasKey;

/**
 * Applies api validation to all rest controllers. The api definition for each request is selected from all given
 * api definitions urls in bmwfd.apidoc.urls based on the requests servlet path.
 */
//@Configuration
public class OpenApiValidationConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiValidationConfig.class);

    private String apidocUrl = "apidoc/index.yaml";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(openApiValidationInterceptor());
    }

    @Bean
    public OpenApiValidationFilter openApiValidationFilter() {
        return new OpenApiValidationFilter(true, true);
    }

    @Bean
    public AsyncHandlerInterceptor openApiValidationInterceptor() {
        List<ApiValidator> apiValidators = Stream.of(apidocUrl)
                .map(ApiValidator::load)
                .collect(Collectors.toList());


        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                return selectValidator(request).preHandle(request, response, handler);
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                selectValidator(request).postHandle(request, response, handler, modelAndView);
            }

            private AsyncHandlerInterceptor selectValidator(HttpServletRequest request) {
                return apiValidators.stream()
                        .filter(apiValidator -> apiValidator.matches(request))
                        .map(AsyncHandlerInterceptor.class::cast)
                        .findFirst()
                        .orElse(new HandlerInterceptorAdapter() {});
            }
        };
    }

    private static class ApiValidator implements AsyncHandlerInterceptor {

        private final ApiOperationResolver resolver;
        private final OpenApiValidationInterceptor interceptor;

        private ApiValidator(ApiOperationResolver resolver, OpenApiValidationInterceptor interceptor) {
            this.resolver = resolver;
            this.interceptor = interceptor;
        }

        public static ApiValidator load(String url) {
            final OpenAPIParser openAPIParser = new OpenAPIParser();
            final ParseOptions parseOptions = new ParseOptions();
            parseOptions.setResolve(true);
            SwaggerParseResult parseResult = openAPIParser.readLocation(url, null, parseOptions);
            ApiOperationResolver resolver = new ApiOperationResolver(parseResult.getOpenAPI(), "/api");
            OpenApiInteractionValidator validator = OpenApiInteractionValidator.createFor(url)
                    .withWhitelist(ValidationErrorsWhitelist.create()
                            .withRule(
                                    "Additional properterties in request are allowed and ignored",
                                    messageHasKey("validation.request.body.schema.additionalProperties"))
                            .withRule(
                                    "TODO: ignored for now, but should not ignore",
                                    messageHasKey("validation.request.body.schema.type"))
                            .withRule(
                                    "TODO: ignored for now, but should not ignore",
                                    messageHasKey("validation.response.body.schema.type")))
                    .build();
            LOGGER.info("Created api validator for url: {}", url);
            return new ApiValidator(resolver, new OpenApiValidationInterceptor(validator));
        }

        public boolean matches(HttpServletRequest servletRequest) {
            ApiOperationMatch match = resolver.findApiOperation(servletRequest.getServletPath(), Request.Method.valueOf(servletRequest.getMethod()));
            return match.isPathFound() && match.isOperationAllowed();
        }

        @Override
        public boolean preHandle(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Object handler) throws Exception {
            return interceptor.preHandle(servletRequest, servletResponse, handler);
        }

        @Override
        public void postHandle(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Object handler, ModelAndView modelAndView) throws Exception {
            interceptor.postHandle(servletRequest, servletResponse, handler, modelAndView);
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            interceptor.afterCompletion(request, response, handler, ex);
        }

        @Override
        public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            interceptor.afterConcurrentHandlingStarted(request, response, handler);
        }
    }
}
