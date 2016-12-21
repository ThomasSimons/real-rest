package org.base.dto;

import org.base.entities.Book;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
public class BookDTO {
    private Integer id;
    private Integer authorId;

    private String title;
    private int numberOfPages;

    public BookDTO() {

    }

    public BookDTO(Book book) {
        id = book.getId();
        authorId = book.getAuthor().getId();
        title = book.getTitle();
        numberOfPages = book.getNumberOfPages();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
