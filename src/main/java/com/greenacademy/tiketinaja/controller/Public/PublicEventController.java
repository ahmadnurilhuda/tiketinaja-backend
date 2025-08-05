package com.greenacademy.tiketinaja.controller.Public;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.dto.response.EventPublicResponse;
import com.greenacademy.tiketinaja.service.EventService;

@RestController
public class PublicEventController {
    
    private final EventService eventService;

    public PublicEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/public/events/{slug}")
    public ResponseEntity<ApiResponse<EventPublicResponse>> getEventBySlug(@PathVariable String slug) {
        return ResponseEntity
                .ok(new ApiResponse<EventPublicResponse>(true, "Success Get Event", eventService.getEventBySlug(slug)));
    }

    @GetMapping("/public/events")
    public ResponseEntity<ApiResponse<Page<EventPublicResponse>>> getAllEvent(
            Pageable pageable,
            @RequestParam(required = false) String title, @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer cityId, @RequestParam(required = false) Integer eventCategoryId,
            @RequestParam(required = false) Integer provinceId) {
        return ResponseEntity.ok(new ApiResponse<Page<EventPublicResponse>>(true, "Success Get All Event",
                eventService.getEvents(provinceId, title, eventCategoryId, cityId, sort, pageable)));
    }
}
