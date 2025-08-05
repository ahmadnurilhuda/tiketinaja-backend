package com.greenacademy.tiketinaja.controller.buyer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.models.Payment;
import com.greenacademy.tiketinaja.service.PaymentService;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/buyer/payments/{id}")
    public ResponseEntity<ApiResponse<Payment>> getAllPayment(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<Payment>(true, "Success Get All Payment", paymentService.getPayment(id)));
    }
}
