package com.example.petstore.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemType; // PET or PRODUCT
    private Long refId;      // Pet.id or Product.id
    private String title;    // breed/name
    private BigDecimal price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
}
