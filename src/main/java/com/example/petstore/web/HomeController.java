package com.example.petstore.web;

import com.example.petstore.repo.PetRepository;
import com.example.petstore.model.Pet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final PetRepository petRepository;

    public HomeController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Pet> top = petRepository.findTop5ByOrderBySoldCountDesc();
        model.addAttribute("topPets", top);
        return "index";
    }

    @GetMapping("/about")
    public String about() { return "about"; }

    @GetMapping("/contact")
    public String contact() { return "contact"; }
}
