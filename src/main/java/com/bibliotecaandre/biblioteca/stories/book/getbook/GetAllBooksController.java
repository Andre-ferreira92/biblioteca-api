package com.bibliotecaandre.biblioteca.stories.book.getbook;

import com.bibliotecaandre.biblioteca.model.Book;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Books", description = "Gestão do catálogo de livros")
@RequestMapping("/books")
public class GetAllBooksController {

    private final GetAllBooksService getAllBooksService;

    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = getAllBooksService.findAllBooks();
        return ResponseEntity.ok(books);
    }
}
