package com.example.assignment5.controller;

import com.example.assignment5.dto.AuthorDto;
import com.example.assignment5.model.Author;
import com.example.assignment5.service.AuthorServiceImpl;
import com.example.assignment5.service.exceptions.AuthorNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorServiceImpl authorService;

    public AuthorController(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable @Valid int id)
            throws AuthorNotFoundException {
        return new ResponseEntity<>(authorService.findAuthorById(id), HttpStatus.OK);
    }

    @GetMapping("/{name}/{surname}")
    public ResponseEntity<Author> getAuthor(@PathVariable @Valid String name,
                                                @PathVariable @Valid String surname)
            throws AuthorNotFoundException {
        return new ResponseEntity<>(authorService.findAuthorByNameAndSurname(name, surname), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(authorService.readAllAuthors());
    }

    @PostMapping("/save")
    public ResponseEntity<Author> createAuthor(@RequestBody @Valid AuthorDto authorDto) {
        return new ResponseEntity<>(authorService.saveAuthor(authorDto), HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Boolean> updateAuthor(@RequestBody @Valid AuthorDto authorDto,
                                                  @PathVariable int id) throws AuthorNotFoundException {
        return new ResponseEntity<>(authorService.updateAuthor(authorDto, id), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteAuthor(@PathVariable @Valid int id)
            throws AuthorNotFoundException {
        return new ResponseEntity<>(authorService.deleteAuthor(id), HttpStatus.OK);
    }
}
