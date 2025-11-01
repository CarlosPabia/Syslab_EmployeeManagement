package com.example.petstore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String breed;
    private String displayName;
    private BigDecimal price;
    private String imageUrl;
    private int stock;
    private long soldCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

@Column(columnDefinition = "TEXT")
private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public long getSoldCount() { return soldCount; }
    public void setSoldCount(long soldCount) { this.soldCount = soldCount; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
