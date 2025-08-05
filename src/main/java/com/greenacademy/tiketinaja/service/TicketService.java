package com.greenacademy.tiketinaja.service;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greenacademy.tiketinaja.dto.response.TicketUserResponse;
import com.greenacademy.tiketinaja.exception.ResourceNotFoundException;
import com.greenacademy.tiketinaja.repositories.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepo;
    public TicketService(TicketRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public Page<TicketUserResponse> getAllByUserIdAndOrderStatusPaid(Integer userId, Pageable pageable) {
        String sortBy = "createdAt";
        Sort.Direction direction = Sort.Direction.DESC;
        pageable = PageRequest.of(pageable.getPageNumber(), 5, Sort.by(direction, sortBy));
        return ticketRepo.findAllPaidTicketsByUserId(userId, pageable);
    }

    public TicketUserResponse getTicketByUser(Integer userId, Integer ticketId) {
        return ticketRepo.findPaidTicketByUserId(userId, ticketId).orElseThrow(()-> new ResourceNotFoundException("Ticket not found"));
    }
    
}
