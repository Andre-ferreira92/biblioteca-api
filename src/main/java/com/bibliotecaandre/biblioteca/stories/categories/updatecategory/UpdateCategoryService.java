package com.bibliotecaandre.biblioteca.stories.categories.updatecategory;

import com.bibliotecaandre.biblioteca.dto.RequestCategoryDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseCategoryDTO;
import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
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
public class UpdateCategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseCategoryDTO updateCategory(Long id, RequestCategoryDTO dto) {
        log.info("Updating category with ID: {}", id);

        Category category = findCategoryById(id);
        validateCategoryNameUniqueness(category.getName(), dto.name());
        updateCategoryName(category, dto.name());
        Category savedCategory = saveCategory(category);

        log.info("Category with ID {} successfully updated", id);
        return buildCategoryResponseDTO(savedCategory);
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private void validateCategoryNameUniqueness(String currentName, String newName) {
        if (!currentName.equalsIgnoreCase(newName)) {
            if (categoryRepository.existsByNameIgnoreCase(newName)) {
                log.warn("Category already exists: {}", newName);
                throw new BusinessRuleException("A category with this name already exists");
            }
        }
    }

    private void updateCategoryName(Category category, String newName) {
        category.setName(newName);
    }

    private Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    private ResponseCategoryDTO buildCategoryResponseDTO(Category category) {
        return new ResponseCategoryDTO(
                category.getId(),
                category.getName()
        );
    }
}
