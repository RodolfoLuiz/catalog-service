package com.catalog.domain.exceptions;
import com.catalog.domain.validation.Error;
import java.util.List;

public class DomainException extends NoStacktraceException {

    private final List<Error> errors;

    private DomainException(final String aMessage, final List<Error> errors) {
        super(aMessage);
        this.errors = errors;
    }

    public static DomainException with(final Error errors) {
        return new DomainException(errors.message(),List.of(errors));
    }

    public static DomainException with(final List<Error> errors) {
        return new DomainException(errors.getFirst().message(),errors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
