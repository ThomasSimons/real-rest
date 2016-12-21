package org.base.rest;

import org.base.dto.AuthorDTO;
import org.base.dto.AuthorForCreationDTO;
import org.base.entities.Author;
import org.base.repositories.AuthorRepository;
import org.base.rest.validation.DTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/authors")
@RestController
public class AuthorEndpoint {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    @Qualifier("standardDtoValidator")
    private DTOValidator validator;

    @RequestMapping(method = RequestMethod.GET, produces = {"application/json", "application/xml", "text/xml"})
    public List<AuthorDTO> getAll() {
        return authorRepository.findAll().stream().map(AuthorDTO::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {"application/json", "application/xml", "text/xml"})
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable("id") Integer id) {
        if (id == null || id < 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Author author = authorRepository.findOne(id);

        if (author == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(new AuthorDTO(author));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json", "application/xml", "text/xml"}, produces = {"application/json", "application/xml", "text/xml"})
    public ResponseEntity addAuthor(@RequestBody AuthorForCreationDTO dto) {
        if (dto == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if (validator.validate(dto).notValid()) {
            return ResponseEntity.unprocessableEntity().body(validator.getViolations());
        }

        Author author = new Author();
        author.setName(dto.getName());

        try {
            authorRepository.save(author);
            return ResponseEntity.created(URI.create("/authors/").resolve(author.getId().toString())).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
