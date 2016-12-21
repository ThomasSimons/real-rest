package org.base.rest.validation;

import java.util.List;

public interface DTOValidator {

    DTOValidator validate(Object dto);

    boolean isValid();

    default boolean notValid() {
        return !isValid();
    }

    List<ValidationError> getViolations();
}
