package com.bibliotecaandre.biblioteca.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("The requested resource was not found in the database.");
    }
}
