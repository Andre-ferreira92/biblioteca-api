package com.bibliotecaandre.biblioteca.stories.categories.updatecategory;


import com.bibliotecaandre.biblioteca.dto.RequestCategoryDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseCategoryDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Gestão das categorias dos livros")
@AllArgsConstructor
public class UpdateCategoryController {

    private final UpdateCategoryService updateCategoryService;

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCategoryDTO> updateCategory(@PathVariable Long id, @RequestBody RequestCategoryDTO dto) {
        ResponseCategoryDTO responseCategoryDTO = updateCategoryService.updateCategory(id,dto);
        return ResponseEntity.ok(responseCategoryDTO);
    }
}
