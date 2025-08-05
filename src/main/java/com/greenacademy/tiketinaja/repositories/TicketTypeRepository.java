package com.greenacademy.tiketinaja.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.greenacademy.tiketinaja.models.TicketType;

public interface TicketTypeRepository extends CrudRepository<TicketType, Integer> {

    Optional<TicketType> findById(Integer id);
    @Query("SELECT t FROM TicketType t JOIN OrderItem oi ON oi.ticketType = t WHERE oi.id = :orderItemId")
    Optional<TicketType> findByOrderItemId(@Param("orderItemId") Integer orderItemId);
    Iterable<TicketType> findAllByEventId(Integer eventId);
}
