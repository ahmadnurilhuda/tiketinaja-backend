package com.greenacademy.tiketinaja.dto.request;

import java.time.Instant;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EventRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @NotNull(message = "Event Category is required")
    private Integer eventCategoryId;

    @NotBlank(message = "Description is required")
    @Size(min = 11, message = "Description must be more than 10 characters")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @NotBlank(message = "Requirements is required")
    @Size(min = 11, message = "Requirements must be more than 10 characters")
    @Size(max = 500, message = "Requirements must be less than 500 characters")
    private String requirements;

    @NotNull(message = "Start Date is required")
    @FutureOrPresent(message = "Start Date must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant startDate;

    @NotNull(message = "End Date is required")
    @FutureOrPresent(message = "End Date must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant endDate;

    @NotBlank(message = "Venue Name is required")
    @Size(min = 3, message = "Venue Name must be at least 3 characters")
    @Size(max = 100, message = "Venue Name must be less than 100 characters")
    private String venueName;

    @NotBlank(message = "Venue Address is required")
    @Size(min = 3, message = "Venue Address must be at least 3 characters")
    @Size(max = 100, message = "Venue Address must be less than 100 characters")
    private String venueAddress;

    @NotNull(message = "City is required")
    private Integer cityId;

    private boolean isOnline;

    @NotNull(message = "Poster is required")
    private MultipartFile poster;

    @NotNull(message = "Venue Layout is required")
    private MultipartFile venueLayout;

    public EventRequest() {
    }

    public EventRequest(String title, Integer eventCategoryId, String description, String requirements, Instant startDate,
            Instant endDate, String venueName, String venueAddress, Integer cityId, boolean isOnline, MultipartFile poster,
            MultipartFile venueLayout) {
        this.title = title;
        this.eventCategoryId = eventCategoryId;
        this.description = description;
        this.requirements = requirements;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.cityId = cityId;
        this.isOnline = isOnline;
        this.poster = poster;
        this.venueLayout = venueLayout;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEventCategoryId() {
        return eventCategoryId;
    }

    public void setEventCategoryId(Integer eventCategoryId) {
        this.eventCategoryId = eventCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public Integer getCityId() {
        return cityId;
    }


    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public MultipartFile getPoster() {
        return poster;
    }

    public void setPoster(MultipartFile poster) {
        this.poster = poster;
    }

    public MultipartFile getVenueLayout() {
        return venueLayout;
    }

    public void setVenueLayout(MultipartFile venueLayout) {
        this.venueLayout = venueLayout;
    }

}
