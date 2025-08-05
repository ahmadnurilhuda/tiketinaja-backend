package com.greenacademy.tiketinaja.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    Page<Order> findAllByUserId(Integer userId,Pageable pageable);
}
