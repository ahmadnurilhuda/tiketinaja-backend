package com.greenacademy.tiketinaja.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.dto.request.LoginRequest;
import com.greenacademy.tiketinaja.dto.request.RegisterRequest;
import com.greenacademy.tiketinaja.dto.request.ResendCodeRequest;
import com.greenacademy.tiketinaja.models.User;
import com.greenacademy.tiketinaja.service.EmailService;
import com.greenacademy.tiketinaja.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class AuthController {
    private final UserService userService;
    private final EmailService emailService;

    public AuthController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new ApiResponse<String>(true, "Success Login", userService.login(loginRequest)));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @ModelAttribute RegisterRequest registerRequest) {
        return ResponseEntity
                .ok(new ApiResponse<User>(true, "Success Register", userService.register(registerRequest)));
    }

    @GetMapping("/auth/verify/{token}")
    public ResponseEntity<ApiResponse<User>> verify(@PathVariable String token) {
        return ResponseEntity.ok(new ApiResponse<User>(true, "Success Verify", userService.verifyEmail(token)));
    }

    @PostMapping("/auth/new-verify")
    public ResponseEntity<ApiResponse<String>> resendCode(@Valid @RequestBody ResendCodeRequest resendRequest) {
        return ResponseEntity.ok(new ApiResponse<String>(true, "Succes Resend Verification Code",
                emailService.resendCodeVerification(resendRequest.getEmail())));
    }

    @GetMapping("/auth/profile")
    public ResponseEntity<ApiResponse<User>> getProfile(HttpServletRequest request) {
        User userLogin = (User) request.getAttribute("user");
        return ResponseEntity.ok(new ApiResponse<User>(true, "Success Get Profile", userService.getProfile(userLogin.getId())));
    }
}
