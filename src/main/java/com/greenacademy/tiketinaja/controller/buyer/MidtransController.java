package com.greenacademy.tiketinaja.controller.buyer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.service.PaymentService;

@RestController
public class MidtransController {

    private final PaymentService paymentService;
    private ObjectMapper objectMapper;
    private final String MIDTRANS_SERVER_KEY;

    public MidtransController(PaymentService paymentService,
            @Value("${midtrans.server.key}") String MIDTRANS_SERVER_KEY, ObjectMapper objectMapper) {
        this.paymentService = paymentService;
        this.MIDTRANS_SERVER_KEY = MIDTRANS_SERVER_KEY;
    }

    @PostMapping("/midtrans/callback")
    public ResponseEntity<ApiResponse<String>> midtransCallback(@RequestBody Map<String, Object> payload) {

        Map<String, Object> payloadChecked = paymentService.checkSignature(MIDTRANS_SERVER_KEY, payload);
        paymentService.savePayment(payloadChecked, MIDTRANS_SERVER_KEY);
        return ResponseEntity.ok(new ApiResponse<String>(true, "Success", null));
       
    }
}
