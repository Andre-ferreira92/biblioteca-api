package com.bibliotecaandre.biblioteca.stories.categories.insertcategory;


import com.bibliotecaandre.biblioteca.dto.RequestCategoryDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseCategoryDTO;
import com.bibliotecaandre.biblioteca.model.Category;
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
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Gestão das categorias dos livros")
@AllArgsConstructor
public class InsertCategoryController {

    private final InsertCategoryService insertCategoryService;

    @PostMapping
    public ResponseEntity<ResponseCategoryDTO> insertCategory(@Valid @RequestBody RequestCategoryDTO dto) {
        ResponseCategoryDTO responseCategoryDTO = insertCategoryService.insertCategory(dto);
        return new ResponseEntity<>(responseCategoryDTO, HttpStatus.CREATED);
    }
}
