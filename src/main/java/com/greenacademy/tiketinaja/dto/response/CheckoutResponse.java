package com.greenacademy.tiketinaja.dto.response;

public class CheckoutResponse {

    private Integer orderId;
    private String paymentUrl;

    public CheckoutResponse() {
    }

    public CheckoutResponse(Integer orderId, String paymentUrl) {
        this.orderId = orderId;
        this.paymentUrl = paymentUrl;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
