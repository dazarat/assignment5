package com.example.assignment5.controller;

import com.example.assignment5.dto.BookDto;
import com.example.assignment5.model.Book;
import com.example.assignment5.service.BookServiceImpl;
import com.example.assignment5.service.exceptions.AuthorNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable @Valid int id)
            throws AuthorNotFoundException {
        return new ResponseEntity<>(bookService.findBookById(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.readAllBooks());
    }

    @PostMapping("/save")
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookDto bookDto)
            throws AuthorNotFoundException {
        return new ResponseEntity<>(bookService.saveBook(bookDto), HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Boolean> updateBook(@RequestBody @Valid BookDto bookDto,
                                               @PathVariable int id) throws AuthorNotFoundException {
        return new ResponseEntity<>(bookService.updateBook(bookDto, id), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable @Valid int id)
            throws AuthorNotFoundException {
        return new ResponseEntity<>(bookService.deleteBook(id), HttpStatus.OK);
    }
}
