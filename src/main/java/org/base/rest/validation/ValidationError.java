package org.base.rest.validation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationError {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String field;
    private String message;

    public ValidationError() {

    }

    public ValidationError(String message) {
        this.message = message;
    }

    public static List<ValidationError> create(String... messages) {
        return Arrays.stream(messages).map(ValidationError::new).collect(Collectors.toList());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
