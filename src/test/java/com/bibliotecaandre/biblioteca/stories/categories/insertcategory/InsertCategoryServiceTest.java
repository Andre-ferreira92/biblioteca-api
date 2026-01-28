package com.bibliotecaandre.biblioteca.stories.categories.insertcategory;

import com.bibliotecaandre.biblioteca.dto.RequestCategoryDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseCategoryDTO;
import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
import com.bibliotecaandre.biblioteca.model.Category;
import com.bibliotecaandre.biblioteca.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InsertCategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    InsertCategoryService insertCategoryService;

    @Test
    void insertCategory() {

        RequestCategoryDTO requestCategoryDTO = new RequestCategoryDTO("terror");
        Category category = new Category();
        category.setId(1L);
        category.setName("terror");

        when(categoryRepository.existsByNameIgnoreCase("terror")).thenReturn(false);
        when(categoryRepository.save(any())).thenReturn(category);

        ResponseCategoryDTO responseCategoryDTO = insertCategoryService.insertCategory(requestCategoryDTO);

        assertEquals(1L, responseCategoryDTO.id());
        assertEquals("terror", responseCategoryDTO.name());

        verify(categoryRepository, times(1)).existsByNameIgnoreCase("terror");
        verify(categoryRepository, times(1)).save(any());
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void insertCategoryWhenCategoryAlreadyExistsThrowsException() {

        when(categoryRepository.existsByNameIgnoreCase("terror")).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> insertCategoryService.insertCategory(new RequestCategoryDTO("terror")));

        verify(categoryRepository, times(1)).existsByNameIgnoreCase("terror");
        verifyNoMoreInteractions(categoryRepository);
    }
}