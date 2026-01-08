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
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updateBook = bookService.updatebook(id,bookDetails);
        return ResponseEntity.ok(updateBook);
    }
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book bookDetails) {
        Book addBook = bookService.addBook(bookDetails);
        return ResponseEntity.ok(addBook);
    }
}
