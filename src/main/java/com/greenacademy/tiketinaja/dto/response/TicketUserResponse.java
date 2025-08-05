package com.greenacademy.tiketinaja.dto.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TicketUserResponse {
    private Integer id;
    private String posterUrl;
    private String eventTitle;
    private String ticketTypeName;
    private String ticketTypeDescription;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    private Instant eventStartDate;
    private String uniqueCode;
    private boolean isUsed;
    private String organizerName;
    private String userFullName;

    public TicketUserResponse() {
    }

    public TicketUserResponse(
    Integer id,
    String posterUrl,
    String eventTitle,
    String ticketTypeName,
    String ticketTypeDescription,
    Instant eventStartDate,
    String uniqueCode,
    Boolean isUsed,
    String organizerName,
    String userFullName
) {
    this.id = id;
    this.posterUrl = posterUrl;
    this.eventTitle = eventTitle;
    this.ticketTypeName = ticketTypeName;
    this.ticketTypeDescription = ticketTypeDescription;
    this.eventStartDate = eventStartDate;
    this.uniqueCode = uniqueCode;
    this.isUsed = isUsed;
    this.organizerName = organizerName;
    this.userFullName = userFullName;
}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }

    public String getTicketTypeDescription() {
        return ticketTypeDescription;
    }

    public void setTicketTypeDescription(String ticketTypeDescription) {
        this.ticketTypeDescription = ticketTypeDescription;
    }

    public Instant getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Instant eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getUserFullName() {
        return userFullName;
    }
    public void setUserFullName(String userFullName){
        this.userFullName = userFullName;
    }
}
