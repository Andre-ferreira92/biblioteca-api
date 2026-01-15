package com.bibliotecaandre.biblioteca.stories.categories.searchcategories;

import com.bibliotecaandre.biblioteca.dto.ResponseCategoryDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Gestão das categorias dos livros")
@AllArgsConstructor
public class SearchCategoriesController {

    private final SearchCategoriesService searchCategoriesService;

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDTO>> getAllCategories() {
        List<ResponseCategoryDTO> responseCategoryDTOS = searchCategoriesService.getAllCategories();
        return ResponseEntity.ok(responseCategoryDTOS);
    }
}
