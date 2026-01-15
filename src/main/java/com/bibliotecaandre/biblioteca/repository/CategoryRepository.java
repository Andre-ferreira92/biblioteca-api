package com.bibliotecaandre.biblioteca.repository;

import com.bibliotecaandre.biblioteca.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCase(String name);
}
