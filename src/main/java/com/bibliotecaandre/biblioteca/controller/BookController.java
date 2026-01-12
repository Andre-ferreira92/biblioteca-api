package com.bibliotecaandre.biblioteca.controller;

import com.bibliotecaandre.biblioteca.model.Book;
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
        Book updateBook = bookService.updateBook(id,bookDetails);
        return ResponseEntity.ok(updateBook);
    }
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book bookDetails) {
        Book addBook = bookService.addBook(bookDetails);
        return new ResponseEntity<>(bookDetails, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
