package com.bibliotecaandre.biblioteca.controller;

import com.bibliotecaandre.biblioteca.dto.ResponseBookCopyDTO;
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
    public ResponseEntity<List<ResponseBookCopyDTO>> getAllAvailableBookCopies() {
        List<ResponseBookCopyDTO> booksAvailable = bookCopyService.findAllAvailableBookCopies();
        return new ResponseEntity<>(booksAvailable, HttpStatus.OK);
    }
    @PostMapping("/{id}/add-copies")
    public ResponseEntity<Void> addBookCopies(@PathVariable Long id,@RequestParam int quantity) {
        bookCopyService.addBookCopies(id,quantity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}