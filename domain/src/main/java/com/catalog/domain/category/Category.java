package com.catalog.domain.category;

import com.catalog.domain.AggregateRoot;
import com.catalog.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryId> {
    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final CategoryId id,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCategory(final String name, final String description, final boolean active){
        final CategoryId id = CategoryId.unique();
        final var now = Instant.now();
        final var deletedAt = active?null:now;
        return new Category(id, name, description, active, now, now, deletedAt);
    }

    public CategoryId getId() {
        return id;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category deactivate() {
        final Instant now = Instant.now();
        if(getDeletedAt() == null){
            this.deletedAt = now;
        }
        this.active = false;
        this.updatedAt = now;
        return this;
    }

    public Category activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category update(final String name, final String description, final boolean active) {
        if(active){
            activate();
        } else{
            deactivate();
        }
        this.name = name;
        this.description = description;
        this.updatedAt = Instant.now();
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}