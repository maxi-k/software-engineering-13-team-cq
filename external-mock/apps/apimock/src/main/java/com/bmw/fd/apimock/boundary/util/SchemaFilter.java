package com.bmw.fd.apimock.boundary.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.models.media.Schema;

import java.util.Map;
import java.util.function.Function;

public class SchemaFilter implements Function<ObjectNode, ObjectNode> {

    private final Schema schema;

    public SchemaFilter(Schema schema) {
        this.schema = schema;
    }

    @Override
    public ObjectNode apply(ObjectNode node) {
        return filter(node, schema);
    }

    private static ObjectNode filter(ObjectNode node, Schema schema) {
        ObjectNode filtered = node.deepCopy();
        Map<String, Schema> properties = schema.getProperties();
        filtered.retain(properties.keySet());
        filtered.fieldNames().forEachRemaining(field -> {
            Schema fieldSchema = properties.get(field);
            if (("object".equals(fieldSchema.getType()) || fieldSchema.getType() == null) && filtered.has(field)) {
                filtered.replace(field, filter((ObjectNode) filtered.get(field), fieldSchema));
            }
        });
        return filtered;
    }
}
