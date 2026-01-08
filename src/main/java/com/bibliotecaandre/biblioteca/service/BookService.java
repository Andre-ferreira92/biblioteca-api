package com.bibliotecaandre.biblioteca.service;

import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.model.BookCopyStatus;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    //lista de todos os livros
    public List<Book> findAllBooks() {
        return bookRepository.findAll();

    }
}
