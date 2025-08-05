package com.greenacademy.tiketinaja.dto.request;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TicketTypeRequest {

    @NotNull(message = "Event ID is required")
    private Integer eventId;

    @NotBlank(message = "Name is required")
    @Size(min = 3,max = 100, message = "Name must be at least 3 characters and less than 100 characters")
    private String name;

    private String description;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private BigDecimal price;

    @NotNull(message = "Start Date is required")
    @FutureOrPresent(message = "Start Date must be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant startDate;

    @NotNull(message = "End Date is required")
    @FutureOrPresent(message = "End Date must be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant endDate;

    public TicketTypeRequest() {
    }

    public TicketTypeRequest(Integer eventId, String name, String description, Integer quantity, BigDecimal price, Instant startDate, Instant endDate) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
}
