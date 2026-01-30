package com.bibliotecaandre.biblioteca.exceptions;

public class IsbnAlreadyExistsException extends RuntimeException {

    public IsbnAlreadyExistsException() {
        super("ISBN already exists.");
    }
}