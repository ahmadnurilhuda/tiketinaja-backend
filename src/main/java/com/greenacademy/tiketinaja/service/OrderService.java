package com.greenacademy.tiketinaja.service;

import org.springframework.stereotype.Service;

import com.greenacademy.tiketinaja.exception.ResourceNotFoundException;
import com.greenacademy.tiketinaja.models.Order;
import com.greenacademy.tiketinaja.repositories.OrderRepository;

@Service
public class OrderService {

    private OrderRepository orderRepo;

    public OrderService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }
    public Order getOrder (Integer id) {
        return orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }    
}
