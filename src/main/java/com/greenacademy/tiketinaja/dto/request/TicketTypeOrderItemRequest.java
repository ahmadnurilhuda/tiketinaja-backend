package com.greenacademy.tiketinaja.dto.request;

public class TicketTypeOrderItemRequest {

    private Integer ticketTypeId;
    private Integer quantity;


    public TicketTypeOrderItemRequest() {
    }

    public TicketTypeOrderItemRequest(Integer ticketTypeId, Integer quantity) {
        this.ticketTypeId = ticketTypeId;
        this.quantity = quantity;
    }

    public Integer getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(Integer ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
}
