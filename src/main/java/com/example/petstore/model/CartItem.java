package com.example.petstore.model;

import java.math.BigDecimal;

public class CartItem {
    private Long refId;
    private String refType;
    private String name;
    private BigDecimal price;
    private int qty;
    private String imageUrl;

    public CartItem() {}
    public CartItem(Long refId, String refType, String name, BigDecimal price, int qty, String imageUrl) {
        this.refId = refId; this.refType = refType; this.name = name; this.price = price; this.qty = qty; this.imageUrl = imageUrl;
    }
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
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
