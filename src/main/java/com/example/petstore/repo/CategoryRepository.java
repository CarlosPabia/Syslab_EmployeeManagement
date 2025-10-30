package com.example.petstore.repo;

import com.example.petstore.model.Category;
import com.example.petstore.model.CategoryKind;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByKind(CategoryKind kind);
}
