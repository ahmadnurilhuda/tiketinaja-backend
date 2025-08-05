package com.greenacademy.tiketinaja.controller.buyer;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.dto.response.TicketUserResponse;
import com.greenacademy.tiketinaja.models.User;
import com.greenacademy.tiketinaja.service.TicketService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/buyer/tickets")
    public ResponseEntity<ApiResponse<Page<TicketUserResponse>>> getAllTicketByUser(HttpServletRequest request, Pageable pageable) {
        User userLogin = (User) request.getAttribute("user");
        return ResponseEntity.ok(new ApiResponse<Page<TicketUserResponse>>(true, "Success Get All Ticket", ticketService.getAllByUserIdAndOrderStatusPaid(userLogin.getId(), pageable)));
    }

    @GetMapping("/buyer/tickets/{id}")
    public ResponseEntity<ApiResponse<TicketUserResponse>> getTicketByUser(@PathVariable Integer id, HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        return ResponseEntity.ok(new ApiResponse<TicketUserResponse>(true, "Success Get All Ticket", ticketService.getTicketByUser(userLogin.getId(), id)));
    }
}
