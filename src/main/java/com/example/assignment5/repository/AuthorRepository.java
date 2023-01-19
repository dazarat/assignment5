package com.example.assignment5.repository;

import com.example.assignment5.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Author findAuthorById(Long id);

    Author findAuthorByNameAndSurname(String name, String surname);
}
