package com.greenacademy.tiketinaja.service;

import org.springframework.stereotype.Service;

import com.greenacademy.tiketinaja.exception.ResourceNotFoundException;
import com.greenacademy.tiketinaja.models.TransactionType;
import com.greenacademy.tiketinaja.repositories.TransactionTypeRepository;

@Service
public class TransactionTypeService {

    private final TransactionTypeRepository transactionTypeRepo;

    public TransactionTypeService(TransactionTypeRepository transactionTypeRepo) {
        this.transactionTypeRepo = transactionTypeRepo;
    }

    public TransactionType getByName(String name) {
        return transactionTypeRepo.findByName(name).orElseThrow(()-> new ResourceNotFoundException ("Transaction Type Not Found"));
    }
    
}
