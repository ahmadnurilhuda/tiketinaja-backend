package com.greenacademy.tiketinaja.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.greenacademy.tiketinaja.repositories.OrderItemRepository;
import com.greenacademy.tiketinaja.models.OrderItem;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepo;

    public OrderItemService(OrderItemRepository orderItemRepo) {
        this.orderItemRepo = orderItemRepo;
    }

    public List<OrderItem> getAllByOrderId(Integer orderId) {
        return orderItemRepo.findByOrderId(orderId);
    }

    
    
}
