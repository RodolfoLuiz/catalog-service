package com.catalog.domain.category;

import com.catalog.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CategoryId extends Identifier {
    private final String value;
    private CategoryId(final String value) {
        Objects.requireNonNull(value, "'value' cannot be null");
        this.value = value;
    }

    public static CategoryId unique(){
        return CategoryId.from(UUID.randomUUID());
    }
    public static CategoryId from(final UUID value) {
        return CategoryId.from(value.toString());
    }
    public static CategoryId from(final String value) {
        return new CategoryId(value.toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryId that = (CategoryId) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}

