package com.greenacademy.tiketinaja.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.models.User;
import com.greenacademy.tiketinaja.service.UserService;
import com.greenacademy.tiketinaja.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private JwtUtil jwtUtil;
    private UserService userService;

    public AuthInterceptor(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        String authorization = request.getHeader("Authorization");

        if (authorization == null || authorization.isEmpty() || !authorization.startsWith("Bearer ")) {
            ApiResponse<Object> apiResponse = new ApiResponse<>(false,
                    "Unauthenticated : Missing or invalid token format in header", null);
            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writeValueAsString(apiResponse);

            response.setContentType("application/json");
            response.setStatus(401);
            response.getWriter().write(result);
            return false;
        }

        String jwtToken = authorization.substring(7);

        if (!jwtUtil.validateToken(jwtToken)) {
            ApiResponse<Object> apiResponse = new ApiResponse<>(false,
                    "Unauthenticated : Missing or invalid token format in token", null);
            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writeValueAsString(apiResponse);

            response.setContentType("application/json");
            response.setStatus(401);
            response.getWriter().write(result);
            return false;
        }

        String emailRequest = jwtUtil.getEmailFromToken(jwtToken);

        User user = userService.getByEmail(emailRequest);
        if (user == null) {
            ApiResponse<Object> apiResponse = new ApiResponse<>(false, "Unauthenticated: User not found", null);
            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writeValueAsString(apiResponse);

            response.setContentType("application/json");
            response.setStatus(401);
            response.getWriter().write(result);
            return false;
        }
        request.setAttribute("user", user);
        return true;
    }
}
