package org.base.rest;

import org.base.dto.BookDTO;
import org.base.dto.BookForCreationDTO;
import org.base.entities.Author;
import org.base.entities.Book;
import org.base.repositories.AuthorRepository;
import org.base.repositories.BookRepository;
import org.base.rest.validation.DTOValidator;
import org.base.rest.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/books")
@RestController
public class BookEndpoint {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    @Qualifier("standardDtoValidator")
    private DTOValidator validator;

    @RequestMapping(method = RequestMethod.GET, produces = {"application/json", "application/xml", "text/xml"})
    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {"application/json", "application/xml", "text/xml"})
    public ResponseEntity<BookDTO> getBook(@PathVariable("id") Integer id) {
        if (id == null || id < 0) {
            return ResponseEntity.badRequest().body(null);
        }

        Book book = bookRepository.findOne(id);

        if (book == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(new BookDTO(book));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json", "application/xml", "text/xml"}, produces = {"application/json", "application/xml", "text/xml"})
    public ResponseEntity addBook(@RequestBody BookForCreationDTO dto) {
        if (dto == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if (validator.validate(dto).notValid()) {
            return ResponseEntity.unprocessableEntity().body(validator.getViolations());
        }

        Book book = new Book();
        book.setTitle(dto.getTitle());

        Author author = authorRepository.findOne(dto.getAuthor());
        if (author == null) {
            return ResponseEntity.unprocessableEntity().body(ValidationError.create("Author not found"));
        }

        book.setAuthor(author);
        book.setNumberOfPages(dto.getNumberOfPages());

        try {
            bookRepository.save(book);
            return ResponseEntity.created(URI.create("/books/").resolve(author.getId().toString())).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
