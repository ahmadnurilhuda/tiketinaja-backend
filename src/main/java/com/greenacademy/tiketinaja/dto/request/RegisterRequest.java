package com.greenacademy.tiketinaja.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    
    @Size(min = 3, message = "Fullname must be at least 3 characters")
    @NotBlank(message = "Fullname is required")
    private String fullName;

    
    @Size(min = 3, message = "Nickname must be at least 3 characters")
    @NotBlank(message = "Nickname is required")
    private String nickName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private MultipartFile profilePicture;

    public RegisterRequest() {
    }

    public RegisterRequest(String fullName, String nickName, String email, String password, String phoneNumber,
            MultipartFile profilePicture) {
        this.fullName = fullName;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }
}
