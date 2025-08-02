package com.greenacademy.tiketinaja.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {

    Optional <Payment> findByOrderId(Integer orderId);
}
