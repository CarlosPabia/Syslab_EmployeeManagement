package com.example.petstore.repo;

import com.example.petstore.model.Product;
import com.example.petstore.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByType(ProductType type);

    List<Product> findByNameContainingIgnoreCase(String query);
}
