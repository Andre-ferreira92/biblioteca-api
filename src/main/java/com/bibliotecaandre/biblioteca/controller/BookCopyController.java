package com.bibliotecaandre.biblioteca.controller;

import com.bibliotecaandre.biblioteca.model.Book;
import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.service.BookCopyService;
import com.bibliotecaandre.biblioteca.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/copies")
@AllArgsConstructor
public class BookCopyController {

    private final BookCopyService bookCopyService;

    @PostMapping()
    public ResponseEntity<BookCopy> createBook(@RequestBody BookCopy bookCopy) {
        BookCopy savedBook = bookCopyService.save(bookCopy);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }
}