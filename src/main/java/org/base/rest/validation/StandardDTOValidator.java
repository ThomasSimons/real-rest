package org.base.rest.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Component(value = "standardDtoValidator")
public class StandardDTOValidator implements DTOValidator {

    @Autowired
    private Validator validator;

    private List<ValidationError> violations;

    @Override
    public DTOValidator validate(Object dto) {
        violations = null;
        violations = validator.validate(dto).stream().map(violation -> {
            ValidationError error = new ValidationError();
            error.setMessage(violation.getMessage());
            return error;
        }).collect(Collectors.toList());
        return this;
    }

    @Override
    public boolean isValid() {
        return violations == null || violations.isEmpty();
    }

    @Override
    public List<ValidationError> getViolations() {
        return violations;
    }
}
