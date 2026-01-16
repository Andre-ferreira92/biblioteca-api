package com.bibliotecaandre.biblioteca.stories.book.insertbook;

import com.bibliotecaandre.biblioteca.dto.RequestBookDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseBookDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Books", description = "Gestão do catálogo de livros")
@RequestMapping("/books")
@AllArgsConstructor
public class InsertBookController {
    InsertBookService insertBookService;

    @PostMapping
    public ResponseEntity<ResponseBookDTO> createBook(@Valid @RequestBody RequestBookDTO bookDetails) {
        ResponseBookDTO createdBook = insertBookService.createBook(bookDetails);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }
}
