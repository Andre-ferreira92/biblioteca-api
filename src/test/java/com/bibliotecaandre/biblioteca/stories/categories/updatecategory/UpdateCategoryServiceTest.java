package com.bibliotecaandre.biblioteca.stories.categories.updatecategory;

import com.bibliotecaandre.biblioteca.dto.RequestCategoryDTO;
import com.bibliotecaandre.biblioteca.dto.ResponseCategoryDTO;
import com.bibliotecaandre.biblioteca.exceptions.BusinessRuleException;
import com.bibliotecaandre.biblioteca.exceptions.ResourceNotFoundException;
import com.bibliotecaandre.biblioteca.model.Category;
import com.bibliotecaandre.biblioteca.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    UpdateCategoryService updateCategoryService;

    @Test
    void updateCategorySuccess() {

        RequestCategoryDTO requestCategoryDTO = new RequestCategoryDTO("test");

        Category categoryExists = new Category();
        categoryExists.setId(1L);
        categoryExists.setName("test");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryExists));
        when(categoryRepository.save(categoryExists)).thenReturn(categoryExists);

        ResponseCategoryDTO responseCategoryDTO = updateCategoryService.updateCategory(1L, requestCategoryDTO);

        assertEquals(1L, responseCategoryDTO.id());
        assertEquals("test", responseCategoryDTO.name());

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(categoryExists);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void updateCategoryWhenCategoryNotFoundThrowsException() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> updateCategoryService.updateCategory(1L, new RequestCategoryDTO("test")));

        verify(categoryRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(categoryRepository);

    }

    @Test
    void updateCategoryWhenCategoryHasSameNameThrowsException() {

        RequestCategoryDTO requestDTO = new RequestCategoryDTO("Nome Duplicado");

        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setName("Nome Antigo");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.existsByNameIgnoreCase("Nome Duplicado")).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> updateCategoryService.updateCategory(1L, requestDTO));

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).existsByNameIgnoreCase("Nome Duplicado");
        verifyNoMoreInteractions(categoryRepository);

    }
}