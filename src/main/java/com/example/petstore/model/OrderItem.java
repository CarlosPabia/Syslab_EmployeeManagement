package com.example.petstore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long refId;
    private String refType; // PET or PRODUCT
    private String name;
    private BigDecimal price;
    private int qty;

    @ManyToOne(fetch=FetchType.LAZY)
    private Order order;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRefId() { return refId; }
    public void setRefId(Long refId) { this.refId = refId; }
    public String getRefType() { return refType; }
    public void setRefType(String refType) { this.refType = refType; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
