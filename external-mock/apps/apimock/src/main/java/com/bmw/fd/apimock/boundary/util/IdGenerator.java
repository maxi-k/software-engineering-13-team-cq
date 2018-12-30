package com.bmw.fd.apimock.boundary.util;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.UUID;
import java.util.function.Function;

public class IdGenerator implements Function<ObjectNode, ObjectNode> {

    private final String idField;

    public IdGenerator(String idField) {
        this.idField = idField;
    }

    @Override
    public ObjectNode apply(ObjectNode node) {
        if (!node.hasNonNull(idField)) {
            node.put(idField, UUID.randomUUID().toString());
        }
        return node;
    }
}
