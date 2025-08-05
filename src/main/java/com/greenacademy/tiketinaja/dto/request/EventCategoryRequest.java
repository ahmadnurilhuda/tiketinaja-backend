package com.greenacademy.tiketinaja.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EventCategoryRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 11, message = "Description must be more than 10 characters")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    public EventCategoryRequest() {
    }

    public EventCategoryRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
