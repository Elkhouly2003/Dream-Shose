package com.example.dream_shops.repository;

import com.example.dream_shops.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByName(String name);

    boolean existsByName(String name);
}
