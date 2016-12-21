package org.base.dto;

import org.base.entities.Author;

public class AuthorDTO {
    private Integer id;
    private String name;

    public AuthorDTO() {
    }

    public AuthorDTO(Author author) {
        id = author.getId();
        name = author.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
