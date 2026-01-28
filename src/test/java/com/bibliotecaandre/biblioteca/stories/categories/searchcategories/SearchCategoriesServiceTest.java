package com.bibliotecaandre.biblioteca.stories.categories.searchcategories;

import com.bibliotecaandre.biblioteca.dto.RequestCategoryDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseCategoryDTO;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Category;
import com.bibliotecaandre.biblioteca.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchCategoriesServiceTest {

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    SearchCategoriesService searchCategoriesService;

    @Test
    void getAllCategoriesSuccess() {

        Category category = new Category();
        category.setId(1L);
        category.setName("test1");

        Category category1 = new Category();
        category1.setId(2L);
        category1.setName("test2");

        List<Category> categories = Arrays.asList(category, category1);

        when(categoryRepository.findAll()).thenReturn(categories);

        List<ResponseCategoryDTO> responseCategories = searchCategoriesService.getAllCategories();

        assertEquals(2,responseCategories.size());
        assertEquals("test1", responseCategories.get(0).name());
        assertEquals("test2", responseCategories.get(1).name());

        verify(categoryRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void getAllCategoriesWhenReturnsListEmpty(){

        when(categoryRepository.findAll()).thenReturn(List.of());

        List<ResponseCategoryDTO> responseCategoryDTO = searchCategoriesService.getAllCategories();

        assertEquals(0, responseCategoryDTO.size());

        verify(categoryRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }
}