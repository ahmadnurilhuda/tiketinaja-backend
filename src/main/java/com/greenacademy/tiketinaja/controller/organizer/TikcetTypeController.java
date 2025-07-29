package com.greenacademy.tiketinaja.controller.organizer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.dto.request.TicketTypeRequest;
import com.greenacademy.tiketinaja.exception.ForbiddenException;
import com.greenacademy.tiketinaja.models.Event;
import com.greenacademy.tiketinaja.models.Organizer;
import com.greenacademy.tiketinaja.models.TicketType;
import com.greenacademy.tiketinaja.models.User;
import com.greenacademy.tiketinaja.service.EventService;
import com.greenacademy.tiketinaja.service.OrganizerService;
import com.greenacademy.tiketinaja.service.TicketTypeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class TikcetTypeController {

    private final TicketTypeService ticketTypeService;
    private final OrganizerService organizerService;
    private final EventService eventService;

    public TikcetTypeController(TicketTypeService ticketTypeService, OrganizerService organizerService, EventService eventService) {
        this.ticketTypeService = ticketTypeService;
        this.organizerService = organizerService;
        this.eventService = eventService;
    }

    @GetMapping("/organizer/ticket-types/{eventId}/event")
    public ResponseEntity<ApiResponse<Iterable<TicketType>>> getAllTicketType(@PathVariable Integer eventId, HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        Organizer organizer = organizerService.getProfile(userLogin.getId());
        Event event = eventService.getEvent(eventId);
        if (organizer.getId() != event.getOrganizer().getId()) {
            throw new ForbiddenException("You are not allowed to get this ticket type");
        }
        return ResponseEntity.ok(new ApiResponse<Iterable<TicketType>>(true, "Success Get All Ticket Type",
                ticketTypeService.getAllTicketType(eventId)));
    }

    @GetMapping("/organizer/ticket-types/{id}")
    public ResponseEntity<ApiResponse<TicketType>> getTicketType(@PathVariable Integer id, HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        Organizer organizer = organizerService.getProfile(userLogin.getId());
        Iterable<Event> events = eventService.getEventByOrganizer(organizer.getId());
        return ResponseEntity
                .ok(new ApiResponse<TicketType>(true, "Success Get Ticket Type", ticketTypeService.getTicketType(id , events)));
    }

    @PostMapping("/organizer/ticket-types/create")
    public ResponseEntity<ApiResponse<TicketType>> createTicketType(
            @Valid @RequestBody TicketTypeRequest ticketTypeRequest) {
        return ResponseEntity.ok(new ApiResponse<TicketType>(true, "Success Create Ticket Type",
                ticketTypeService.create(ticketTypeRequest)));
    }

    @PutMapping("/organizer/ticket-types/update/{id}")
    public ResponseEntity<ApiResponse<TicketType>> updateTicketType(@PathVariable Integer id,
            @Valid @RequestBody TicketTypeRequest ticketTypeRequest, HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        Organizer organizer = organizerService.getProfile(userLogin.getId());
        return ResponseEntity.ok(new ApiResponse<TicketType>(true, "Success Update Ticket Type",
                ticketTypeService.update(ticketTypeRequest, id, organizer.getId())));
    }
    @DeleteMapping("/organizer/ticket-types/delete/{id}")
    public ResponseEntity<ApiResponse<TicketType>> deleteTicketType(@PathVariable Integer id,
            HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        Organizer organizer = organizerService.getProfile(userLogin.getId());
        return ResponseEntity
                .ok(new ApiResponse<TicketType>(true, "Success Delete Ticket Type", ticketTypeService.delete(id, organizer.getId())));
    }
}
