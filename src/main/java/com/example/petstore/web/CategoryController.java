package com.example.petstore.web;

import com.example.petstore.model.Category;
import com.example.petstore.model.CategoryKind;
import com.example.petstore.repo.CategoryRepository;
import com.example.petstore.repo.PetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final PetRepository petRepository;

    public CategoryController(CategoryRepository categoryRepository, PetRepository petRepository) {
        this.categoryRepository = categoryRepository;
        this.petRepository = petRepository;
    }

    @GetMapping("/domestic")
    public String domestic(Model model) {
        List<Category> categories = categoryRepository.findByKind(CategoryKind.DOMESTIC);
        model.addAttribute("categories", categories);
        model.addAttribute("headline", "Domestic Pets");
        return "categories";
    }

    @GetMapping("/exotic")
    public String exotic(Model model) {
        List<Category> categories = categoryRepository.findByKind(CategoryKind.EXOTIC);
        model.addAttribute("categories", categories);
        model.addAttribute("headline", "Exotic Pets");
        return "categories";
    }

    @GetMapping("/category/{id}")
    public String categoryDetail(@PathVariable Long id, Model model) {
        Category cat = categoryRepository.findById(id).orElseThrow();
        model.addAttribute("category", cat);
        model.addAttribute("pets", petRepository.findByCategory(cat));
        return "category_detail";
    }
}
