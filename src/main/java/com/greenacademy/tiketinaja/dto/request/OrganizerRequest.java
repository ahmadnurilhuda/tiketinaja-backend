package com.greenacademy.tiketinaja.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class OrganizerRequest {

    @NotBlank(message = "Name Organizer is required")
    private String name;

    @NotBlank(message = "Email Organizer is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone Number Organizer is required")
    private String phoneNumber;

    private String websiteUrl;
    private MultipartFile profilePicture;
    private String biography;
    
    public OrganizerRequest() {
    }
    public OrganizerRequest(String name, String email, String phoneNumber, String websiteUrl,
            MultipartFile profilePicture, String biography) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.websiteUrl = websiteUrl;
        this.profilePicture = profilePicture;
        this.biography = biography;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
