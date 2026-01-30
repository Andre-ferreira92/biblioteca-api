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
        Category category = categoryRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        if (!category.getName().equalsIgnoreCase(dto.name())) {
            if (categoryRepository.existsByNameIgnoreCase(dto.name())) {
                log.warn("Category already exists");
                throw new BusinessRuleException("A category with this name already exists");
            }
        }
        category.setName(dto.name());

        categoryRepository.save(category);
        log.info("Category with ID {} successfully updated", id);

        return new ResponseCategoryDTO(
                category.getId(),
                category.getName()
        );
    }
}
