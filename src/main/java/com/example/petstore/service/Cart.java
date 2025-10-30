package com.example.petstore.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@Scope("session")
public class Cart {
    public static class Item {
        public String itemType; // PET or PRODUCT
        public Long refId;
        public String title;
        public BigDecimal price;
        public int qty = 1;
    }

    private final Map<String, Item> items = new LinkedHashMap<>(); // key: itemType#refId

    private String key(String type, Long id) { return type + "#" + id; }

    public void add(String type, Long id, String title, BigDecimal price) {
        String k = key(type, id);
        items.compute(k, (kk, existing) -> {
            if (existing == null) {
                Item it = new Item();
                it.itemType = type;
                it.refId = id;
                it.title = title;
                it.price = price;
                it.qty = 1;
                return it;
            } else {
                existing.qty += 1;
                return existing;
            }
        });
    }

    public void remove(String type, Long id) {
        items.remove(key(type, id));
    }

    public void clear() { items.clear(); }

    public Collection<Item> getItems() { return items.values(); }

    public BigDecimal total() {
        return items.values().stream()
                .map(i -> i.price.multiply(BigDecimal.valueOf(i.qty)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isEmpty() { return items.isEmpty(); }
}
