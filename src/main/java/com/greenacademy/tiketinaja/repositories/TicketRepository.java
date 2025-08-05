package com.greenacademy.tiketinaja.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.greenacademy.tiketinaja.dto.response.TicketUserResponse;
import com.greenacademy.tiketinaja.models.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {

    @Query("""
            SELECT new com.greenacademy.tiketinaja.dto.response.TicketUserResponse(
                t.id,
                e.posterUrl,
                e.title,
                tt.name,
                tt.description,
                e.startDate,
                t.uniqueCode,
                t.isUsed,
                o.name,
                u.fullName
            )
            FROM Ticket t
            JOIN t.orderItem oi
            JOIN oi.order ord
            JOIN oi.ticketType tt
            JOIN tt.event e
            JOIN e.organizer o
            JOIN ord.user u
            WHERE ord.status = 'PAID'
            AND u.id = :userId
            """)
    Page<TicketUserResponse> findAllPaidTicketsByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Query("""
                SELECT new com.greenacademy.tiketinaja.dto.response.TicketUserResponse(
                    t.id,
                    e.posterUrl,
                    e.title,
                    tt.name,
                    tt.description,
                    e.startDate,
                    t.uniqueCode,
                    t.isUsed,
                    o.name,
                    u.fullName
                )
                FROM Ticket t
                JOIN t.orderItem oi
                JOIN oi.order ord
                JOIN oi.ticketType tt
                JOIN tt.event e
                JOIN e.organizer o
                JOIN ord.user u
                WHERE ord.status = 'PAID'
                AND u.id = :userId
                AND t.id = :ticketId
            """)
    Optional<TicketUserResponse> findPaidTicketByUserId(
            @Param("userId") Integer userId,
            @Param("ticketId") Integer ticketId);

}
