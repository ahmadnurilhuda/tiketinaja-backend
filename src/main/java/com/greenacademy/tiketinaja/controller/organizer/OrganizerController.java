package com.greenacademy.tiketinaja.controller.organizer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.dto.request.OrganizerRequest;
import com.greenacademy.tiketinaja.models.Organizer;
import com.greenacademy.tiketinaja.models.User;
import com.greenacademy.tiketinaja.service.OrganizerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class OrganizerController {

    private final OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @PostMapping("/organizer/register")
    public ResponseEntity<ApiResponse<Organizer>> createOrganizer(@Valid @ModelAttribute OrganizerRequest register,
            HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        return ResponseEntity.ok(
                new ApiResponse<Organizer>(true, "Success Register", organizerService.register(register, userLogin)));
    }
    
    @GetMapping("/organizer/profile")
    public ResponseEntity<ApiResponse<Organizer>> getProfile(HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        return ResponseEntity.ok(new ApiResponse<Organizer>(true, "Success Get Profile Organizer", organizerService.getProfile(userLogin.getId())));
    }

    
}
