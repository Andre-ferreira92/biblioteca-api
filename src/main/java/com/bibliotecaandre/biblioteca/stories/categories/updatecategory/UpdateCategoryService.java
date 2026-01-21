package com.bibliotecaandre.biblioteca.stories.categories.updatecategory;


import com.bibliotecaandre.biblioteca.dto.RequestCategoryDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseCategoryDTO;
import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Category;
import com.bibliotecaandre.biblioteca.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class UpdateCategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public ResponseCategoryDTO updateCategory(Long id, RequestCategoryDTO dto) {
        log.info("A atualizar a categoria com id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        if (!category.getName().equalsIgnoreCase(dto.name())) {
            if (categoryRepository.existsByNameIgnoreCase(dto.name())) {
                log.warn("Esta categoria ja existe");
                throw new BusinessRuleException("Categoria com esse nome ja existe");
            }
        }
        category.setName(dto.name());

        categoryRepository.save(category);
        log.info("A categoria com id {} atualizada", id);

        return new ResponseCategoryDTO(
                category.getId(),
                category.getName()
        );
    }
}
