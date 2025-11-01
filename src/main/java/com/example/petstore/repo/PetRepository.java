package com.example.petstore.repo;

import com.example.petstore.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findTop5ByOrderBySoldCountDesc();
Page<Pet> findByCategoryId(Long categoryId, Pageable pageable);
    List<Pet> findByBreedContainingIgnoreCase(String query);

    Page<Pet> findByCategoryIdOrderByPriceAsc(Long categoryId, Pageable pageable);
    Page<Pet> findByCategoryIdOrderByPriceDesc(Long categoryId, Pageable pageable);
}
