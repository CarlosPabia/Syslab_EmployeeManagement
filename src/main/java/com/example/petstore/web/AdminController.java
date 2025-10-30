package com.example.petstore.web;

import com.example.petstore.model.*;
import com.example.petstore.repo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CategoryRepository categoryRepository;
    private final PetRepository petRepository;
    private final ProductRepository productRepository;

    public AdminController(CategoryRepository categoryRepository, PetRepository petRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.petRepository = petRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/login")
    public String login() { return "admin_login"; }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("petsCount", petRepository.count());
        model.addAttribute("productsCount", productRepository.count());
        model.addAttribute("domesticCategories", categoryRepository.findByKind(CategoryKind.DOMESTIC).size());
        model.addAttribute("exoticCategories", categoryRepository.findByKind(CategoryKind.EXOTIC).size());
        return "admin_dashboard";
    }

    // Simple forms to add/edit/delete Pets (similar could be added for categories and products)
    @GetMapping("/pets/new")
    public String newPet(Model model) {
        model.addAttribute("pet", new Pet());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin_pet_form";
    }

    @PostMapping("/pets/save")
    public String savePet(@ModelAttribute Pet pet, @RequestParam Long categoryId,
                          @RequestParam(required=false) String priceStr) {
        categoryRepository.findById(categoryId).ifPresent(pet::setCategory);
        if (priceStr != null && !priceStr.isBlank()) pet.setPrice(new BigDecimal(priceStr));
        petRepository.save(pet);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/pets/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        petRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }
}
