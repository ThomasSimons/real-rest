package org.base.dto;

import org.base.entities.Author;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class AuthorForCreationDTO {

    @NotNull
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
