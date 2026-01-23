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
        // 1. Validar se o nome já existe
        log.info("A criar uma nova categoria");
        if (categoryRepository.existsByNameIgnoreCase(dto.name())) {
            log.warn("Esta categoria ja existe");
            throw new BusinessRuleException("A categoria '" + dto.name() + "' já existe.");
        }

        // 2. Criar a nova entidade
        Category newCategory = new Category();
        newCategory.setName(dto.name());

        Category savedCategory = categoryRepository.save(newCategory);
        log.info("Uma nova categoria foi criada: {}", savedCategory.getName());

        // 4. Retornar o DTO (incluindo o ID que a DB gerou)
        return new ResponseCategoryDTO(
                savedCategory.getId(),
                savedCategory.getName()
        );
    }
}
