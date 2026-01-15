package com.bibliotecaandre.biblioteca.stories.categories.searchcategories;


import com.bibliotecaandre.biblioteca.dto.ResponseCategoryDTO;
import com.bibliotecaandre.biblioteca.model.Category;
import com.bibliotecaandre.biblioteca.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchCategoriesService {

    private final CategoryRepository categoryRepository;

    public List<ResponseCategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(cat -> new ResponseCategoryDTO(
                        cat.getId(),
                        cat.getName()
                ))
                .toList();
    }
}