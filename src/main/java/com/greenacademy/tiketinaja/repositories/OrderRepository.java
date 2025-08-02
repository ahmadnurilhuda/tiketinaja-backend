package com.greenacademy.tiketinaja.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    
}
