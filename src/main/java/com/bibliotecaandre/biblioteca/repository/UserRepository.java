package com.bibliotecaandre.biblioteca.repository;

import com.bibliotecaandre.biblioteca.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
