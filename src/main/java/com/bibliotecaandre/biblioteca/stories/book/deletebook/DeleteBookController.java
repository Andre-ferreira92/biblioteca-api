package com.bibliotecaandre.biblioteca.stories.book.deletebook;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@Tag(name = "Books", description = "Gestão do catálogo de livros")
@RequestMapping("/books")
public class DeleteBookController {

    private final DeleteBookService deleteBookService;


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        deleteBookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
