package com.bmw.fd.spring.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URLDecoder;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * JsonFilter that is capable of filtering a json result based on http request query parameters 'fields' and 'expand'.
 * <p>
 * This filter only activates if there is a current request bound in {@link RequestContextHolder}.
 *<p>
 * Usage: register this class as json filter on the spring web mvc object mapper.
 * Annotate all your pojo's with @{@link com.fasterxml.jackson.annotation.JsonFilter}.
 */
public class FieldsFilter extends SimpleBeanPropertyFilter {

    @Override
    public void serializeAsField(Object pojo, JsonGenerator gen, SerializerProvider provider, PropertyWriter writer)
            throws Exception {
        String jsonPath = createPath(writer, gen);
        if (include(writer, jsonPath)) {
            writer.serializeAsField(pojo, gen, provider);
        } else {
            writer.serializeAsOmittedField(pojo, gen, provider);
        }
    }

    private boolean include(PropertyWriter writer, String jsonPath) {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return true;
        }

        return include(ServletUriComponentsBuilder.fromCurrentRequest().build(), writer, jsonPath);
    }

    /**
     * Determine whether to include the current property in json or not. Maybe overridden in sub class.
     *
     * @param uri components of current request uri
     * @param writer json property
     * @param jsonPath path to json property
     * @return true if property has to be included, false otherwise
     */
    protected boolean include(UriComponents uri, PropertyWriter writer, String jsonPath) {
        MultiValueMap<String, String> queryParams = uri.getQueryParams();

        if (writer.findAnnotation(Expandable.class) != null) {
            return queryParams.getOrDefault("expand", Collections.emptyList()).stream()
                    .map(URLDecoder::decode)
                    .flatMap(p -> Stream.of(p.split(",")))
                    .anyMatch(p -> p.startsWith(jsonPath) || jsonPath.startsWith(p));
        }

        if (queryParams.containsKey("fields")) {
            return queryParams.get("fields").stream()
                    .map(URLDecoder::decode)
                    .flatMap(p -> Stream.of(p.split(",")))
                    .anyMatch(p -> p.startsWith(jsonPath) || jsonPath.startsWith(p));
        }

        return true;
    }

    /**
     * Starting with the current property builds the json path to the current property from the root.
     */
    private static String createPath(PropertyWriter writer, JsonGenerator jgen) {
        StringBuilder path = new StringBuilder();
        path.append(writer.getName());
        JsonStreamContext sc = jgen.getOutputContext();
        if (sc != null) {
            sc = sc.getParent();
        }

        while (sc != null) {
            if (sc.getCurrentName() != null && !(sc.getCurrentValue() instanceof CollectionResource)) {
                if (path.length() > 0) {
                    path.insert(0, ".");
                }
                path.insert(0, sc.getCurrentName());
            }
            sc = sc.getParent();
        }
        return path.toString();
    }
}
