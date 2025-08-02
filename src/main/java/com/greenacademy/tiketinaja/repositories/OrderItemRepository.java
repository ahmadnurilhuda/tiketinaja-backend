package com.greenacademy.tiketinaja.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
    
    List<OrderItem> findByOrderId(Integer orderId);

}
