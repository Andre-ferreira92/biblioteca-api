package com.bibliotecaandre.biblioteca.service;

import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();

    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
