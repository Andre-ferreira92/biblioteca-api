package com.bibliotecaandre.biblioteca.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Erro: O recurso solicitado não foi encontrado na base de dados.");
    }
}
