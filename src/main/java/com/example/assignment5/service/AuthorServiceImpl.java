package com.example.assignment5.service;

import com.example.assignment5.dto.AuthorDto;
import com.example.assignment5.model.Author;
import com.example.assignment5.repository.AuthorRepository;
import com.example.assignment5.service.interfaces.AuthorService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author saveAuthor(AuthorDto authorDto) {
        Author author = new Author(0L, authorDto.getName(), authorDto.getSurname(), authorDto.getBooks());
        return authorRepository.save(author);
    }

    public List<Author> readAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    public Author findAuthorById(int id) {
        return authorRepository.findById((long) id).get();
    }

    public Author findAuthorByNameAndSurname(String name, String surname){
        return authorRepository.findAuthorByNameAndSurname(name, surname);
    }

    public boolean updateAuthor(AuthorDto authorDto, int id) {
        if (authorRepository.existsById((long) id)) {
            Author author = new Author((long) id, authorDto.getName(), authorDto.getSurname(), authorDto.getBooks());
            authorRepository.save(author);
            return true;
        }
        return false;
    }

    public boolean deleteAuthor(int id) {
        if (authorRepository.existsById((long) id)) {
            authorRepository.deleteById((long) id);
            return true;
        }
        return false;
    }
}
