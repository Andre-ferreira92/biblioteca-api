package com.bibliotecaandre.biblioteca.stories.book.searchbookwithfilters;

import com.bibliotecaandre.biblioteca.dto.ResponseBookDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Books", description = "Gestão do catálogo de livros")
@RequestMapping("/books")
public class SerchWithFilterController {

    private final SerchWithFilterService getAllBooksService;

    @GetMapping()
    public ResponseEntity<List<ResponseBookDTO>> getAllBooks(@RequestParam(required = false) Long categoryId) {
        List<ResponseBookDTO> books = getAllBooksService.findAllBooks(categoryId);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBookDTO> getBookById(@PathVariable Long id) {
        ResponseBookDTO book = getAllBooksService.findBookById(id);
        return ResponseEntity.ok(book);
    }
}
