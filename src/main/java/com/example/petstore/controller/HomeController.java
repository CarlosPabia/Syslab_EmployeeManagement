package com.example.petstore.controller;

import com.example.petstore.repo.PetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PetRepository petRepo;

    public HomeController(PetRepository petRepo) {
        this.petRepo = petRepo;
    }

    @GetMapping("/")
    public String dashboard(Model model){
        model.addAttribute("topPets", petRepo.findTop5ByOrderBySoldCountDesc());
        model.addAttribute("about", "We’re a friendly neighborhood pet store offering domestic and exotic companions.");
        model.addAttribute("contact", "Email hello@petstore.local · +63 900 000 0000");
        return "index";
    }
}
