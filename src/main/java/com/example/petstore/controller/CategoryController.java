package com.example.petstore.controller;

import com.example.petstore.model.Category;
import com.example.petstore.model.CategoryType;
import com.example.petstore.repo.CategoryRepository;
import com.example.petstore.repo.PetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoryController {

    private final CategoryRepository catRepo;
    private final PetRepository petRepo;

    public CategoryController(CategoryRepository catRepo, PetRepository petRepo) {
        this.catRepo = catRepo;
        this.petRepo = petRepo;
    }

    @GetMapping("/domestic")
    public String domestic(Model model){
        model.addAttribute("categories", catRepo.findByType(CategoryType.DOMESTIC));
        return "domestic";
    }

    @GetMapping("/exotic")
    public String exotic(Model model){
        model.addAttribute("categories", catRepo.findByType(CategoryType.EXOTIC));
        return "exotic";
    }

    @GetMapping("/category/{id}")
    public String categoryPets(@PathVariable Long id, Model model){
        Category c = catRepo.findById(id).orElseThrow();
        model.addAttribute("category", c);
        model.addAttribute("pets", petRepo.findByCategoryId(id));
        return "category-pets";
    }
}
