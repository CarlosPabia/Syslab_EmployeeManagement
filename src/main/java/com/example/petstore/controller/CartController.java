package com.example.petstore.controller;

import com.example.petstore.model.CartItem;
import com.example.petstore.model.Pet;
import com.example.petstore.model.Product;
import com.example.petstore.repo.PetRepository;
import com.example.petstore.repo.ProductRepository;
import com.example.petstore.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    private final CartService cart;
    private final PetRepository petRepo;
    private final ProductRepository productRepo;

    public CartController(CartService cart, PetRepository petRepo, ProductRepository productRepo) {
        this.cart = cart;
        this.petRepo = petRepo;
        this.productRepo = productRepo;
    }

    @PostMapping("/cart/add/pet/{id}")
    public String addPet(@PathVariable Long id){
        Pet p = petRepo.findById(id).orElseThrow();
        cart.add(new CartItem(id, "PET", p.getBreed(), p.getPrice(), 1, p.getImageUrl()));
        return "redirect:/cart";
    }

    @PostMapping("/cart/add/product/{id}")
    public String addProduct(@PathVariable Long id){
        Product pr = productRepo.findById(id).orElseThrow();
        cart.add(new CartItem(id, "PRODUCT", pr.getName(), pr.getPrice(), 1, pr.getImageUrl()));
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model){
        model.addAttribute("items", cart.getItems());
        model.addAttribute("total", cart.total());
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String remove(@RequestParam String key){
        cart.remove(key);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    @ResponseBody
    public java.util.Map<String, Object> updateQuantity(@RequestParam String key, @RequestParam int qty){
        cart.updateQuantity(key, qty);
        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("success", true);
        response.put("total", cart.total());
        return response;
    }

    @PostMapping("/cart/remove-ajax")
    @ResponseBody
    public java.util.Map<String, Object> removeAjax(@RequestParam String key){
        cart.remove(key);
        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("success", true);
        response.put("total", cart.total());
        return response;
    }
}
