package com.example.petstore.repo;

import com.example.petstore.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findTop5ByOrderBySoldCountDesc();
    List<Pet> findByCategoryId(Long categoryId);
}
