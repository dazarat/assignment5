package com.example.assignment5.repository;

import com.example.assignment5.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findBooksByAuthorId(Long id);
}
