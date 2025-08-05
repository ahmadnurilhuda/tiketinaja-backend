package com.greenacademy.tiketinaja.dto.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EventPublicResponse {

    private Integer id;
    private String title;
    private String description;
    private String slug;
    private String requirements;
    private String venueName;
    private String organizerName;
    private String city;
    private String eventCategory;
    private String venueAddress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    private Instant startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    private Instant endDate;
    private String posterUrl;
    private String venueLayoutUrl;

    public EventPublicResponse() {
    }

    public EventPublicResponse(Integer id, String title, String description, String slug, String requirements,
            String venueName, String organizerName, String city, String eventCategory, String venueAddress,
            Instant startDate, Instant endDate, String posterUrl, String venueLayoutUrl) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.slug = slug;
        this.requirements = requirements;
        this.venueName = venueName;
        this.organizerName = organizerName;
        this.city = city;
        this.eventCategory = eventCategory;
        this.venueAddress = venueAddress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.posterUrl = posterUrl;
        this.venueLayoutUrl = venueLayoutUrl;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSlug() {
        return slug;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public String getCity() {
        return city;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getVenueLayoutUrl() {
        return venueLayoutUrl;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setVenueLayoutUrl(String venueLayoutUrl) {
        this.venueLayoutUrl = venueLayoutUrl;
    }
}


