package com.bibliotecaandre.biblioteca.stories.book.updatebook;

import com.bibliotecaandre.biblioteca.dto.RequestBookDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseBookDTO;
import com.bibliotecaandre.biblioteca.model.Book;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Books", description = "Gestão do catálogo de livros")
@RequestMapping("/books")
@AllArgsConstructor
public class UpdateBookController {

    private UpdateBookService updateBookService;

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBookDTO> updateBook(@PathVariable Long id, @RequestBody RequestBookDTO dto) {
        ResponseBookDTO updateBook = updateBookService.updateBook(id,dto);
        return ResponseEntity.ok(updateBook);
    }
}
