package com.example.petstore.controller;

import com.example.petstore.repo.PetRepository;
import com.example.petstore.repo.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    private final PetRepository petRepo;
    private final ProductRepository productRepo;

    public SearchController(PetRepository petRepo, ProductRepository productRepo) {
        this.petRepo = petRepo;
        this.productRepo = productRepo;
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        model.addAttribute("query", query);
        model.addAttribute("pets", petRepo.findByBreedContainingIgnoreCase(query));
        model.addAttribute("products", productRepo.findByNameContainingIgnoreCase(query));
        return "search-results";
    }
}