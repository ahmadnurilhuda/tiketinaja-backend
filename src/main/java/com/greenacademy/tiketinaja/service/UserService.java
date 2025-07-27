package com.greenacademy.tiketinaja.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.greenacademy.tiketinaja.dto.request.LoginRequest;
import com.greenacademy.tiketinaja.dto.request.RegisterRequest;
import com.greenacademy.tiketinaja.enums.Enum.Role;
import com.greenacademy.tiketinaja.exception.ResourceAlreadyExistsException;
import com.greenacademy.tiketinaja.exception.ResourceNotFoundException;
import com.greenacademy.tiketinaja.models.User;
import com.greenacademy.tiketinaja.repositories.UserRepository;
import com.greenacademy.tiketinaja.utils.JwtUtil;

@Service
public class UserService {

    private UserRepository userRepo;
    private EmailService emailService;
    private JwtUtil jwtUtil;

    public UserService(UserRepository userRepo, EmailService emailService, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
    }

    public User getByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found By Email"));
    }

    public User register(RegisterRequest registerRequest) {
        if (userRepo.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exist. Please register with another email");
        }
        User user = new User();
        user.setFullName(registerRequest.getFullName());
        user.setNickName(registerRequest.getNickName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt()));
        user.setRole(Role.BUYER);
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setVerificationCode(UUID.randomUUID().toString());
        long expirationTime = 60 * 30 ; // set selama 30 menit
        user.setVerificationCodeExpiresAt(Instant.now().plusSeconds(expirationTime));

        if (registerRequest.getProfilePicture() != null && !registerRequest.getProfilePicture().isEmpty()) {
            String profilePictureUrl = saveProfilePicture(registerRequest.getProfilePicture());
            user.setProfilePictureUrl(profilePictureUrl);
        }
        userRepo.save(user);
        emailService.sendEmailVerification(user.getEmail(), user.getNickName(), user.getVerificationCodeExpiresAt(),
                user.getVerificationCode());
        return user;
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

            String filePath = "src/main/resources/static/uploads/profile_pictures/" + newFileName;
            Path targetFile = Path.of(filePath);

            Files.copy(profilePicture.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
            String savedPath = "/profile_pictures/" + newFileName;
            return savedPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to save profile picture: " + e.getMessage());
        }
    }

    public User verifyEmail(String verificationCode) {
        User user = userRepo.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found By Verification Code"));
        if (user.getVerificationCodeExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Verification code has expired");
        }
        user.setVerified(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiresAt(null);
        user.setVerifiedAt(Instant.now());
        userRepo.save(user);
        return user;
    }

    public String login(LoginRequest request) {

        if (request.getEmail() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Email and Password are required");
        }

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email or Password is incorrect"));

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Email or Password is incorrect");
        }

        if (!user.isVerified()) {
            throw new IllegalArgumentException("User is not verified");
        }

        String token = jwtUtil.generatedToken(user.getEmail());

        return token;
    }

    public User getProfile (Integer Id){
        return userRepo.findById(Id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
    }
}
