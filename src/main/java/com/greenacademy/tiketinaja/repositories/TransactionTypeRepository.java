package com.greenacademy.tiketinaja.repositories;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.TransactionType;

public interface TransactionTypeRepository extends CrudRepository<TransactionType, Integer> {
    
}
