package com.example.petstore.controller;

import com.example.petstore.service.CartService;
import com.example.petstore.service.CheckoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CheckoutController {

    private final CartService cart;
    private final CheckoutService checkoutService;

    public CheckoutController(CartService cart, CheckoutService checkoutService) {
        this.cart = cart;
        this.checkoutService = checkoutService;
    }

    @GetMapping("/checkout")
    public String form(Model model){
        model.addAttribute("total", cart.total());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String submit(@RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String address,
                         @RequestParam String paymentMethod,
                         @RequestParam String phone,
                         RedirectAttributes ra){
        var order = checkoutService.placeOrder(name,email,address,paymentMethod,phone, cart.getItems());
        cart.clear();
        ra.addFlashAttribute("msg", "Order #" + order.getId() + " placed. Total: " + order.getTotal());
        return "redirect:/";
    }
}
