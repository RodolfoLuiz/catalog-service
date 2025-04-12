package com.catalog.domain.validation.handler;

import com.catalog.domain.exceptions.DomainException;
import com.catalog.domain.validation.Error;
import com.catalog.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {
    private final List<Error> errors;
    private Notification(List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create(){
        return new Notification(new ArrayList<>());
    }
    public static Notification create(final Error error){
        return new Notification(new ArrayList<>()).append(error);
    }
    public static Notification create(final Throwable error){
        return create(new Error(error.getMessage()));
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
        return this;
    }

    @Override
    public Notification validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch(final DomainException ex){
            this.errors.addAll(ex.getErrors());
        } catch(final Throwable ex){
            this.errors.add(new Error(ex.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
