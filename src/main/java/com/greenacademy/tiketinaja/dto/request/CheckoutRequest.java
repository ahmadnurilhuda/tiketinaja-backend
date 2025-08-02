package com.greenacademy.tiketinaja.dto.request;

import java.util.List;

public class CheckoutRequest {

    private List<TicketTypeOrderItemRequest> items;

    public CheckoutRequest() {
    }

    public CheckoutRequest(List<TicketTypeOrderItemRequest> items) {
        this.items = items;
    }

    public List<TicketTypeOrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<TicketTypeOrderItemRequest> items) {
        this.items = items;
    }



}
