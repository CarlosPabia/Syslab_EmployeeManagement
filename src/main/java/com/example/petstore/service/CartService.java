package com.example.petstore.service;

import com.example.petstore.model.CartItem;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.*;

@Service
@SessionScope
public class CartService {
    private final Map<String, CartItem> items = new LinkedHashMap<>();

    private String key(Long id, String type){ return type + "-" + id; }

    public Collection<CartItem> getItems(){ return items.values(); }

    public void add(CartItem ci){
        String k = key(ci.getRefId(), ci.getRefType());
        if(items.containsKey(k)) {
            CartItem old = items.get(k);
            old.setQty(old.getQty() + ci.getQty());
        } else {
            items.put(k, ci);
        }
    }

    public void remove(String key){ items.remove(key); }

public CartItem update(String key, int qty) {
        if (qty <= 0) {
            // Treat 0 or less as a remove
            remove(key);
            return null;
        }
        CartItem item = items.get(key);
        if (item != null) {
            item.setQty(qty);
            return item;
        }
        return null;
    }

    public void clear(){ items.clear(); }

    public BigDecimal total(){
        BigDecimal t = BigDecimal.ZERO;
        for (CartItem i : items.values()) {
            t = t.add(i.getPrice().multiply(BigDecimal.valueOf(i.getQty())));
        }
        return t;
    }
}
