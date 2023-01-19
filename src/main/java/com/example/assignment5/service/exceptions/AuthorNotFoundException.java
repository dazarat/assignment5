package com.example.assignment5.service.exceptions;

public class AuthorNotFoundException extends Exception{

    public AuthorNotFoundException(){
        super("author with this id was not found");
    }
}
