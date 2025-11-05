package com.example.petstore.controller;

import com.example.petstore.model.ProductType;
import com.example.petstore.repo.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    private final ProductRepository productRepo;

    public ProductController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/toys")
    public String toys(Model model){
        model.addAttribute("products", productRepo.findByType(ProductType.TOY));
        return "toys";
    }

    @GetMapping("/food")
    public String food(Model model){
        model.addAttribute("products", productRepo.findByType(ProductType.FOOD));
        return "food";
    }
}
