package com.catalog.application;

import com.catalog.domain.category.Category;

public abstract class UnitUseCase<IN> {
    public abstract void execute(IN input);
}