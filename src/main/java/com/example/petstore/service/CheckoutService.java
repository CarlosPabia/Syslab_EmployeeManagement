package com.example.petstore.service;

import com.example.petstore.model.*;
import com.example.petstore.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;

@Service
public class CheckoutService {

    private final OrderRepository orderRepo;
    private final PetRepository petRepo;
    private final ProductRepository productRepo;

    public CheckoutService(OrderRepository orderRepo, PetRepository petRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.petRepo = petRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public Order placeOrder(String name, String email, String address, String payment, String phone, Collection<CartItem> cart){
        Order o = new Order();
        o.setCustomerName(name);
        o.setEmail(email);
        o.setAddress(address);
        o.setPaymentMethod(payment);
        o.setPhone(phone);
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem ci : cart){
            OrderItem oi = new OrderItem();
            oi.setRefId(ci.getRefId());
            oi.setRefType(ci.getRefType());
            oi.setName(ci.getName());
            oi.setPrice(ci.getPrice());
            oi.setQty(ci.getQty());
            oi.setOrder(o);
            o.getItems().add(oi);

            total = total.add(ci.getPrice().multiply(BigDecimal.valueOf(ci.getQty())));

            if ("PET".equals(ci.getRefType())){
                Pet p = petRepo.findById(ci.getRefId()).orElseThrow();
                p.setStock(Math.max(0, p.getStock() - ci.getQty()));
                p.setSoldCount(p.getSoldCount() + ci.getQty());
            } else {
                Product pr = productRepo.findById(ci.getRefId()).orElseThrow();
                pr.setStock(Math.max(0, pr.getStock() - ci.getQty()));
            }
        }
        o.setTotal(total);
        return orderRepo.save(o);
    }
}
