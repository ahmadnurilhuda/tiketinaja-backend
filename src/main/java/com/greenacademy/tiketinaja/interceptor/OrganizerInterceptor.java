package com.greenacademy.tiketinaja.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.models.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OrganizerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        User user = (User) request.getAttribute("user");
        if (user == null || !user.isOrganizer()) {
            ApiResponse<Object> apiResponse = new ApiResponse<>(false, "Forbidden : Not Organizer", null);
            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writeValueAsString(apiResponse);

            response.setContentType("application/json");
            response.setStatus(403);
            response.getWriter().write(result);
            return false;
        }
        return true;
    }
    
}
