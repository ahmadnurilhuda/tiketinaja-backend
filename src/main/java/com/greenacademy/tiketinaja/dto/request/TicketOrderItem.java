package com.greenacademy.tiketinaja.dto.request;

public class TicketOrderItem {
    private Integer quantity;
    private Integer ticketTypeId;

    public TicketOrderItem(Integer quantity, Integer ticketTypeId) {
        this.quantity = quantity;
        this.ticketTypeId = ticketTypeId;
    }

    public Integer getQuantity() {
        return quantity;
    }
    
}
