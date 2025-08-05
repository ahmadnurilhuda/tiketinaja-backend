package com.greenacademy.tiketinaja.controller.organizer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.dto.request.EventRequest;
import com.greenacademy.tiketinaja.dto.request.EventRequestUpdate;
import com.greenacademy.tiketinaja.exception.ForbiddenException;
import com.greenacademy.tiketinaja.models.Event;
import com.greenacademy.tiketinaja.models.Organizer;
import com.greenacademy.tiketinaja.models.User;
import com.greenacademy.tiketinaja.service.EventService;
import com.greenacademy.tiketinaja.service.OrganizerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class EventController {

    private final EventService eventService;
    private final OrganizerService organizerService;

    public EventController(EventService eventService, OrganizerService organizerService) {
        this.eventService = eventService;
        this.organizerService = organizerService;
    }

    @GetMapping("/organizer/events")
    public ResponseEntity<ApiResponse<Page<Event>>> getAllEvent(Pageable pageable,
            @RequestParam(required = false) String title, @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer cityId, @RequestParam(required = false) Integer eventCategoryId,
            @RequestParam(required = false) Integer provinceId,
            HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        Organizer organizer = organizerService.getProfile(userLogin.getId());
        return ResponseEntity.ok(new ApiResponse<Page<Event>>(true, "Success Get All Event by Organizer",
                eventService.getAllByOrganizer(pageable, title, sort, cityId, eventCategoryId, organizer.getId(),
                        provinceId)));
    }

    @GetMapping("/organizer/events/{id}")
    public ResponseEntity<ApiResponse<Event>> getEvent(@PathVariable Integer id, HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        Organizer organizer = organizerService.getProfile(userLogin.getId());
        if (organizer.getId() != eventService.getEvent(id).getOrganizer().getId()) {
            throw new ForbiddenException("You are not allowed to get this event");
        }
        return ResponseEntity.ok(new ApiResponse<Event>(true, "Success Get Event", eventService.getEvent(id)));
    }
    
    @PostMapping("/organizer/events/create")
    public ResponseEntity<ApiResponse<Event>> createEvent(@Valid @ModelAttribute EventRequest eventRequest,
            HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        Organizer organizer = organizerService.getProfile(userLogin.getId());
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Success Create Event",
                eventService.create(eventRequest, organizer)));
    }

    @DeleteMapping("/organizer/events/delete/{id}")
    public ResponseEntity<ApiResponse<Event>> deleteEvent(@PathVariable Integer id, HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        Organizer organizer = organizerService.getProfile(userLogin.getId());
        if (organizer.getId() != eventService.getEvent(id).getOrganizer().getId()) {
            throw new ForbiddenException("You are not allowed to delete this event");
        }
        return ResponseEntity.ok(new ApiResponse<Event>(true, "Success Delete Event", eventService.delete(id)));
    }

    @PutMapping("/organizer/events/update/{id}")
    public ResponseEntity<ApiResponse<Event>> updateEvent(@PathVariable Integer id,
            @Valid @ModelAttribute EventRequestUpdate eventRequest,
            HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        Organizer organizer = organizerService.getProfile(userLogin.getId());
        if (organizer.getId() != eventService.getEvent(id).getOrganizer().getId()) {
            throw new ForbiddenException("You are not allowed to update this event");
        }
        return ResponseEntity
                .ok(new ApiResponse<Event>(true, "Success Update Event",
                        eventService.update(eventRequest, id)));
    }
}
