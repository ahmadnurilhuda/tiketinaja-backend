package com.greenacademy.tiketinaja.exception;

public class ErrorMessage {
    private String message;
    private String field;

    public ErrorMessage(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setField(String field) {
        this.field = field;
    }
    
}
