package com.example.petstore.controller;

import com.example.petstore.model.CartItem;
import com.example.petstore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/cart/api") // Base path for all cart API calls
public class CartApiController {

    private final CartService cart;

    public CartApiController(CartService cart) {
        this.cart = cart;
    }

    /**
     * Handles AJAX request to remove an item from the cart.
     * Returns the new cart total.
     */
    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, String>> removeItem(@RequestParam String key) {
        cart.remove(key);
        BigDecimal newTotal = cart.total();
        // Return the new total as a JSON object
        return ResponseEntity.ok(Map.of("newTotal", newTotal.toPlainString()));
    }

    /**
     * Handles AJAX request to update an item's quantity.
     * Returns new subtotal and new cart total.
     */
    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> updateItem(
            @RequestParam String key, 
            @RequestParam int qty) {
        
        CartItem updatedItem = cart.update(key, qty);
        BigDecimal newTotal = cart.total();
        
        // Calculate new subtotal (or 0 if item was removed)
        BigDecimal newSubtotal = BigDecimal.ZERO;
        if (updatedItem != null) {
            newSubtotal = updatedItem.getPrice().multiply(BigDecimal.valueOf(updatedItem.getQty()));
        }

        // Return all the data the frontend needs
        return ResponseEntity.ok(Map.of(
            "newTotal", newTotal.toPlainString(),
            "newSubtotal", newSubtotal.toPlainString(),
            "qty", String.valueOf(qty) // Send back qty in case it was 0 (removed)
        ));
    }
}