package com.greenacademy.tiketinaja.repositories;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.TicketType;

public interface TicketTypeRepository extends CrudRepository<TicketType, Integer> {
    
}
