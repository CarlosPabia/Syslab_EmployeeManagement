package com.example.petstore.web.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
public class CheckoutForm {
    @NotBlank private String name;
    @Email @NotBlank private String email;
    @NotBlank private String address;
    @NotBlank private String paymentMethod; // cash, gcash, card
    @NotBlank private String phone;
}
