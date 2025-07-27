package com.greenacademy.tiketinaja.repositories;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
    
}
