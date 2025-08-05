package com.greenacademy.tiketinaja.controller.buyer;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.models.Order;
import com.greenacademy.tiketinaja.models.User;
import com.greenacademy.tiketinaja.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping("/buyer/orders")
    public ResponseEntity<ApiResponse<Iterable<Order>>> getAllOrderByUser(HttpServletRequest request, Pageable pageable) {
        User userLogin = (User) request.getAttribute("user");
        return ResponseEntity.ok(new ApiResponse<Iterable<Order>>(true, "Success Get All Order", orderService.getAllByUserId(userLogin.getId(), pageable)));
    }

    
}
