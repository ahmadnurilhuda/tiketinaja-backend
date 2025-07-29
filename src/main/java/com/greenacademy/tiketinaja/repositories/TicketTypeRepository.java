package com.greenacademy.tiketinaja.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.TicketType;

public interface TicketTypeRepository extends CrudRepository<TicketType, Integer> {

    Optional <TicketType> findById(Integer id);
    Iterable<TicketType> findAllByEventId(Integer eventId);
    
}
