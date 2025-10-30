package com.example.petstore.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // e.g., "Buddy"
    private String breed; // e.g., "Golden Retriever"
    private BigDecimal price;
    private String imageUrl; // may be null
    private boolean available = true;
    private int soldCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
