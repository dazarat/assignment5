package com.example.assignment5.dto;

import jakarta.validation.constraints.NotNull;

public class BookDto {

    @NotNull(message = "Book name should be not null")
    private String bookName;

    @NotNull(message = "Book should have AuthorId")
    private String authorId;

    public BookDto(String bookName, String authorId) {
        this.bookName = bookName;
        this.authorId = authorId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
