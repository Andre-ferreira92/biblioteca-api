package com.bibliotecaandre.biblioteca.stories.physicalbook.addphysicalcopies;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/copies")
@Tag(name = "Copies", description = "Gestão de Copias dos livros")
@RestController
@AllArgsConstructor
public class AddPhysicalCopiesController {

    private final AddPhysicalCopiesService addPhysicalCopiesService;

    @PostMapping("/{id}/add-copies")
    public ResponseEntity<Void> addBookCopies(@PathVariable Long id, @RequestParam int quantity) {
        addPhysicalCopiesService.addBookCopies(id,quantity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
