package com.greenacademy.tiketinaja.controller.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.dto.request.EventCategoryRequest;
import com.greenacademy.tiketinaja.models.EventCategory;
import com.greenacademy.tiketinaja.service.EventCategoryService;

import jakarta.validation.Valid;

@RestController
public class EventCategoryController {

    private final EventCategoryService eventCategoryService;

    public EventCategoryController(EventCategoryService eventCategoryService) {
        this.eventCategoryService = eventCategoryService;
    }

    @GetMapping("/admin/event-categories")
    public ResponseEntity<ApiResponse<Page<EventCategory>>> getAllEventCategory(Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sort) {
        return ResponseEntity.ok(new ApiResponse<Page<EventCategory>>(true, "Success Get All Event Category",
                eventCategoryService.getAll(pageable, name, sort)));
    }

    @GetMapping ("/admin/event-categories/{id}")
    public ResponseEntity<ApiResponse<EventCategory>> getEventCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<EventCategory>(true, "Success Get Event Category", eventCategoryService.get(id)));
    }

    @PostMapping("/admin/event-categories/create")
    public ResponseEntity<ApiResponse<EventCategory>> createEventCategory(@Valid @RequestBody EventCategoryRequest eventCategoryRequest) {
        return ResponseEntity.ok(new ApiResponse<EventCategory>(true, "Success Create Event Category", eventCategoryService.create(eventCategoryRequest)));
    }

    @PutMapping("/admin/event-categories/update/{id}")
    public ResponseEntity<ApiResponse<EventCategory>> updateEventCategory(@PathVariable Integer id, @Valid @RequestBody EventCategoryRequest eventCategoryRequest) {
        return ResponseEntity.ok(new ApiResponse<EventCategory>(true, "Success Update Event Category", eventCategoryService.update(id, eventCategoryRequest)));
    }

    @DeleteMapping("/admin/event-categories/delete/{id}")
    public ResponseEntity<ApiResponse<EventCategory>> deleteEventCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<EventCategory>(true, "Success Delete Event Category", eventCategoryService.delete(id)));
    }
}
