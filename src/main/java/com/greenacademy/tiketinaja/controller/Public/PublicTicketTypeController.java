package com.greenacademy.tiketinaja.controller.Public;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.models.TicketType;
import com.greenacademy.tiketinaja.service.TicketTypeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PublicTicketTypeController {

    private final TicketTypeService ticketTypeService;

    public PublicTicketTypeController(TicketTypeService ticketTypeService) {
        this.ticketTypeService = ticketTypeService;
    }

    @GetMapping("/public/ticket-types/{eventId}/event")
    public ResponseEntity<ApiResponse<Iterable<TicketType>>> getAllTicketType(@PathVariable Integer eventId) {
        return ResponseEntity.ok(new ApiResponse<Iterable<TicketType>>(true, "Success Get All Ticket Type",
                ticketTypeService.getAllTicketType(eventId)));
    }

    
    
}
