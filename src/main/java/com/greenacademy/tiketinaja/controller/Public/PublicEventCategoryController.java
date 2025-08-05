package com.greenacademy.tiketinaja.controller.Public;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.models.EventCategory;
import com.greenacademy.tiketinaja.service.EventCategoryService;

@RestController
public class PublicEventCategoryController {
    private final EventCategoryService eventCategoryService;

    public PublicEventCategoryController(EventCategoryService eventCategoryService) {
        this.eventCategoryService = eventCategoryService;
    }

    @GetMapping("/public/event-categories")
    public ResponseEntity<ApiResponse<Iterable<EventCategory>>> getAllEventCategory() {
        return ResponseEntity.ok(new ApiResponse<Iterable<EventCategory>>(true, "Success Get All Event Category", eventCategoryService.getAll()));
    }
}
