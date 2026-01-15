package com.bibliotecaandre.biblioteca.stories.categories.insertcategory;

import com.bibliotecaandre.biblioteca.dto.RequestCategoryDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseCategoryDTO;
import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
import com.bibliotecaandre.biblioteca.model.Category;
import com.bibliotecaandre.biblioteca.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class InsertCategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public ResponseCategoryDTO insertCategory(RequestCategoryDTO dto) {
        // 1. Validar se o nome já existe
        if (categoryRepository.existsByNameIgnoreCase(dto.name())) {
            throw new BusinessRuleException("A categoria '" + dto.name() + "' já existe.");
        }

        // 2. Criar a nova entidade (o ID será gerado automaticamente)
        Category newCategory = new Category();
        newCategory.setName(dto.name());

        // 3. Gravar na DB
        Category savedCategory = categoryRepository.save(newCategory);

        // 4. Retornar o DTO (incluindo o ID que a DB gerou)
        return new ResponseCategoryDTO(
                savedCategory.getId(),
                savedCategory.getName()
        );
    }
}
