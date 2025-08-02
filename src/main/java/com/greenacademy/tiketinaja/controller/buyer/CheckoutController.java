package com.greenacademy.tiketinaja.controller.buyer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.dto.request.CheckoutRequest;
import com.greenacademy.tiketinaja.dto.response.CheckoutResponse;
import com.greenacademy.tiketinaja.models.User;
import com.greenacademy.tiketinaja.service.CheckoutService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }
    @PostMapping("/checkout/event/ticket-type")
    public ResponseEntity<ApiResponse<CheckoutResponse>> checkout(@RequestBody CheckoutRequest checkoutRequest, HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        return ResponseEntity.ok(new ApiResponse<CheckoutResponse>(true, "Success Checkout", checkoutService.checkout(userLogin, checkoutRequest)));
    }
}
