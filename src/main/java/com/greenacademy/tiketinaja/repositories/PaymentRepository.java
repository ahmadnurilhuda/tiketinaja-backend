package com.greenacademy.tiketinaja.repositories;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {

    
}
