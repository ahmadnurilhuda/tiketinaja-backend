package com.greenacademy.tiketinaja.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.greenacademy.tiketinaja.dto.request.OrganizerRequest;
import com.greenacademy.tiketinaja.exception.ResourceNotFoundException;
import com.greenacademy.tiketinaja.models.Organizer;
import com.greenacademy.tiketinaja.repositories.OrganizerRepository;
import com.greenacademy.tiketinaja.repositories.UserRepository;
import com.greenacademy.tiketinaja.models.User;

@Service
public class OrganizerService {

    private final OrganizerRepository organizerRepo;
    private final UserRepository userRepo;

    public OrganizerService(OrganizerRepository organizerRepo, UserRepository userRepo) {
        this.userRepo = userRepo;
        this.organizerRepo = organizerRepo;
    }

    public Organizer register(OrganizerRequest register, User user) {

        User existUser = userRepo.findById(user.getId()).orElseThrow(()-> new ResourceNotFoundException("User Not Found When Register Organizer"));
        Organizer newOrganizer = new Organizer();
        newOrganizer.setName(register.getName());
        newOrganizer.setEmail(register.getEmail());
        newOrganizer.setPhoneNumber(register.getPhoneNumber());
        newOrganizer.setWebsiteUrl(register.getWebsiteUrl());
        newOrganizer.setBiography(register.getBiography());
        newOrganizer.setUser(existUser);
        if(register.getProfilePicture() != null && !register.getProfilePicture().isEmpty()){
            String profilePictureOrganizer = saveProfilePicture(register.getProfilePicture());
            newOrganizer.setProfilePictureUrl(profilePictureOrganizer);
        }
        existUser.setOrganizer(true); // set isOrganizer to true
        organizerRepo.save(newOrganizer);
        return newOrganizer;
    }

    private String saveProfilePicture(MultipartFile profilePicture) {
        try {
            if (profilePicture == null || profilePicture.isEmpty()) {
                throw new IllegalArgumentException("Profile picture is required");
            }
            if (!profilePicture.getContentType().equals("image/jpeg")
                    && !profilePicture.getContentType().equals("image/png")
                    && !profilePicture.getContentType().equals("image/webp")) {
                throw new IllegalArgumentException("Only JPEG, PNG, and WebP images are allowed");
            }

            if (profilePicture.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("Image size must be less than 5MB");
            }

            String originalFileName = profilePicture.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileExtension;

            String filePath = "src/main/resources/static/uploads/profile_pictures/organizer/" + newFileName;
            Path targetFile = Path.of(filePath);

            Files.copy(profilePicture.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
            String savedPath = "/profile_pictures/organizer/" + newFileName;
            return savedPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to save profile picture: " + e.getMessage());
        }
    }

    public Organizer getProfile(Integer Id){
        return organizerRepo.findByUserId(Id).orElseThrow(()-> new ResourceNotFoundException("Organizer Not Found"));
    }
}
