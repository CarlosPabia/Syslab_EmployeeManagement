package com.example.petstore.repo;

import com.example.petstore.model.Category;
import com.example.petstore.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(CategoryType type);
    Optional<Category> findByNameIgnoreCase(String name);
}
