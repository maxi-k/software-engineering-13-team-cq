package com.bmw.fd.spring.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.ResponseEntity;

public class CollectionResource<T> {

    @JsonSerialize(as = Iterable.class)
    private final Iterable<T> items;

    // needed for reflection magic
    private CollectionResource() {
        this.items = null;
    }

    @JsonCreator
    public CollectionResource(@JsonProperty("items") Iterable<T> items) {
        this.items = items;
    }

    public static <T> ResponseEntity<CollectionResource<T>> okResponse(Iterable<T> body) {
        return ResponseEntity.ok(new CollectionResource<T>(body));
    }

    public Iterable<T> getItems() {
        return items;
    }
}
