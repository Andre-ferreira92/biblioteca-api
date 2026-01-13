package com.bibliotecaandre.biblioteca.stories.physicalbook.getallcopies;

import com.bibliotecaandre.biblioteca.dto.ResponseBookCopyDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/copies")
@Tag(name = "Copies", description = "Gestão de Copias dos livros")
@AllArgsConstructor
public class GetAllCopiesController {

    private final GetAllCopiesService getAllCopiesService;

    @GetMapping
    public ResponseEntity<List<ResponseBookCopyDTO>> getAllAvailableBookCopies() {
        List<ResponseBookCopyDTO> booksAvailable = getAllCopiesService.findAllAvailableBookCopies();
        return new ResponseEntity<>(booksAvailable, HttpStatus.OK);
    }
}
