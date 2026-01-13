package com.bibliotecaandre.biblioteca.exceptions;

public class IsbnAlreadyExistsException extends RuntimeException {

    public IsbnAlreadyExistsException() {
        super("Erro: Este ISBN já está registado na base de dados.");
    }
}