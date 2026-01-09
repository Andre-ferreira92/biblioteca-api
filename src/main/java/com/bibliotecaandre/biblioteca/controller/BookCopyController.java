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

//    @PostMapping()
//    public ResponseEntity<BookCopy> addBookCopies(@PathVariable Long id, @RequestBody BookCopy bookCopy) {
//        BookCopy addBooks = bookCopyService.addBookCopies(bookCopy,id);
//        return new ResponseEntity<>(addBooks, HttpStatus.CREATED);
//    }
}