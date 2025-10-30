package com.example.petstore.web;

import com.example.petstore.model.*;
import com.example.petstore.repo.*;
import com.example.petstore.service.Cart;
import com.example.petstore.web.dto.CheckoutForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class CartController {
    private final Cart cart;
    private final PetRepository petRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CartController(Cart cart, PetRepository petRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.cart = cart;
        this.petRepository = petRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/cart/add/pet/{id}")
    public String addPet(@PathVariable Long id) {
        var pet = petRepository.findById(id).orElseThrow();
        cart.add("PET", id, pet.getBreed(), pet.getPrice());
        return "redirect:/cart";
    }

    @PostMapping("/cart/add/product/{id}")
    public String addProduct(@PathVariable Long id) {
        var p = productRepository.findById(id).orElseThrow();
        cart.add("PRODUCT", id, p.getName(), p.getPrice());
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("cart", cart);
        model.addAttribute("checkoutForm", new CheckoutForm());
        return "cart";
    }

    @PostMapping("/cart/remove")
    public String remove(@RequestParam String type, @RequestParam Long id) {
        cart.remove(type, id);
        return "redirect:/cart";
    }

    @PostMapping("/purchase")
    public String purchase(@Valid @ModelAttribute CheckoutForm checkoutForm, BindingResult result, Model model) {
        if (result.hasErrors() || cart.isEmpty()) {
            model.addAttribute("cart", cart);
            return "cart";
        }
        var order = Order.builder()
                .customerName(checkoutForm.getName())
                .email(checkoutForm.getEmail())
                .address(checkoutForm.getAddress())
                .paymentMethod(checkoutForm.getPaymentMethod())
                .phone(checkoutForm.getPhone())
                .createdAt(LocalDateTime.now())
                .build();
        cart.getItems().forEach(i -> order.getItems().add(OrderItem.builder()
                .itemType(i.itemType).refId(i.refId).title(i.title).price(i.price)
                .quantity(i.qty).order(order).build()));
        orderRepository.save(order);

        // update sold counts for pets
        cart.getItems().stream().filter(i -> "PET".equals(i.itemType)).forEach(i -> {
            petRepository.findById(i.refId).ifPresent(p -> { p.setSoldCount(p.getSoldCount() + i.qty); petRepository.save(p); });
        });

        cart.clear();
        return "redirect:/?purchased=1";
    }
}
