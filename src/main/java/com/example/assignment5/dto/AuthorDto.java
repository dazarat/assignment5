package com.example.assignment5.dto;

import com.example.assignment5.model.Book;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class AuthorDto {
    @NotNull(message = "name should not be null")
    private String name;

    @NotNull(message = "surname should not be null")
    private String surname;

    @Nullable
    private List<Book> books;

    public AuthorDto() {
    }

    public AuthorDto(String name, String surname, List<Book> books) {
        this.name = name;
        this.surname = surname;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
