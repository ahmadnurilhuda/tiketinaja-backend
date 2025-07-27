package com.greenacademy.tiketinaja.repositories;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {
    
}
