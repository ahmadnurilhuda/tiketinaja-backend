package com.greenacademy.tiketinaja.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.TransactionType;

public interface TransactionTypeRepository extends CrudRepository<TransactionType, Integer> {
    Optional <TransactionType> findByName(String name);
    
}
