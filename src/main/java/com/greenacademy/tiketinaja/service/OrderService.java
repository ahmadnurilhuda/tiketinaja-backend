package com.greenacademy.tiketinaja.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Order getOrder(Integer id) {
        return orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public Page<Order> getAllByUserId(Integer userId, Pageable pageable) {
        String sortBy = "createdAt";
        Sort.Direction direction = Sort.Direction.DESC;
        pageable = PageRequest.of(pageable.getPageNumber(), 5, Sort.by(direction, sortBy));
        return orderRepo.findAllByUserId(userId, pageable);
    }
}
