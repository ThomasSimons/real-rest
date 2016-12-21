package org.base.rest.validation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationError {
    private String message;

    public ValidationError() {

    }

    public ValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static List<ValidationError> create(String... messages) {
        return Arrays.stream(messages).map(ValidationError::new).collect(Collectors.toList());
    }
}
