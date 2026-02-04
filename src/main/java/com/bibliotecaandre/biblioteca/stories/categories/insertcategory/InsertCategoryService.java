package com.bibliotecaandre.biblioteca.stories.categories.insertcategory;

import com.bibliotecaandre.biblioteca.dto.RequestCategoryDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseCategoryDTO;
import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
import com.bibliotecaandre.biblioteca.model.Category;
import com.bibliotecaandre.biblioteca.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class InsertCategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseCategoryDTO insertCategory(RequestCategoryDTO dto) {
        log.info("Creating a new category");

        validateCategoryNameExists(dto.name());
        Category newCategory = createCategoryEntity(dto.name());
        Category savedCategory = categoryRepository.save(newCategory);

        log.info("A new category has been created: {}", savedCategory.getName());
        return buildCategoryResponseDTO(savedCategory);
    }

    private void validateCategoryNameExists(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            log.warn("This category already exists: {}", name);
            throw new BusinessRuleException("Category " + name + " already exists");
        }
    }

    private Category createCategoryEntity(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }

    private ResponseCategoryDTO buildCategoryResponseDTO(Category category) {
        return new ResponseCategoryDTO(
                category.getId(),
                category.getName()
        );
    }
}