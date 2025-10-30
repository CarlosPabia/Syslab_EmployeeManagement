package com.example.petstore.web;

import com.example.petstore.model.ProductType;
import com.example.petstore.repo.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/toys")
    public String toys(Model model) {
        model.addAttribute("products", productRepository.findByType(ProductType.TOY));
        model.addAttribute("headline", "Pet Toys");
        return "products";
    }

    @GetMapping("/food")
    public String food(Model model) {
        model.addAttribute("products", productRepository.findByType(ProductType.FOOD));
        model.addAttribute("headline", "Pet Food");
        return "products";
    }
}
