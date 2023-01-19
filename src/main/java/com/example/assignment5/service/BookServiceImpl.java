package com.example.assignment5.service;

import com.example.assignment5.dto.BookDto;
import com.example.assignment5.model.Author;
import com.example.assignment5.model.Book;
import com.example.assignment5.repository.AuthorRepository;
import com.example.assignment5.repository.BookRepository;
import com.example.assignment5.service.exceptions.AuthorNotFoundException;
import com.example.assignment5.service.interfaces.BookService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book saveBook(BookDto bookDto) throws AuthorNotFoundException {
        Author author = authorRepository.findAuthorById(Long.valueOf(bookDto.getAuthorId()));
        if (author == null) {
            throw new AuthorNotFoundException();
        }
        Book book = new Book(0L, bookDto.getBookName(), author);
        return bookRepository.save(book);
    }

    public List<Book> readAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public Book findBookById(int id) {
        return bookRepository.findById((long) id).get();
    }

    public boolean updateBook(BookDto bookDto, int id) {
        if (bookRepository.existsById((long) id)) {
            Author author = authorRepository.findAuthorById(
                    Long.valueOf(bookDto.getAuthorId()));
            Book book = new Book((long) id, bookDto.getBookName(), author);
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    public boolean deleteBook(int id) {
        if (bookRepository.existsById((long) id)) {
            bookRepository.deleteById((long) id);
            return true;
        }
        return false;
    }
}
