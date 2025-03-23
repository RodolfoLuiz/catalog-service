package com.catalog.domain.category;

import com.catalog.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {
    Category create(Category category);
    void deleteById(CategoryId id);
    Optional<Category> findById(CategoryId id);
    Category update(Category category);
    Pagination<Category> findAll(CategorySearchQuery query);
}
