package com.bibliotecaandre.biblioteca.controller;

import com.bibliotecaandre.biblioteca.model.BookCopy;
import com.bibliotecaandre.biblioteca.service.BookCopyService;
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

    @GetMapping
    public ResponseEntity<List<BookCopy>> getAllAvailableBookCopies() {
        List<BookCopy> booksAvailable = bookCopyService.findAllAvailableBookCopies();
        return ResponseEntity.ok(booksAvailable);
    }
    @PostMapping("/{id}/add-copies")
    public ResponseEntity<Void> addBookCopies(@PathVariable Long id,@RequestParam int quantity) {
        bookCopyService.addBookCopies(id,quantity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}