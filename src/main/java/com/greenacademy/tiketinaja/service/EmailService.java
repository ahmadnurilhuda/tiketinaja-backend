package com.greenacademy.tiketinaja.service;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;
import java.time.Instant;
import java.time.ZoneId;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import com.greenacademy.tiketinaja.models.User;
import com.greenacademy.tiketinaja.repositories.UserRepository;
import com.greenacademy.tiketinaja.config.UrlConfig;
import com.greenacademy.tiketinaja.exception.ResourceNotFoundException;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UrlConfig urlConfig;
    private final TemplateEngine templateEngine;
    private final UserRepository userRepo;

    public EmailService(JavaMailSender javaMailSender, UrlConfig urlConfig, TemplateEngine templateEngine,
            UserRepository userRepo) {
        this.userRepo = userRepo;
        this.javaMailSender = javaMailSender;
        this.urlConfig = urlConfig;
        this.templateEngine = templateEngine;
    }

    public void sendEmailVerification(String email, String nickName, Instant expiredCode, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'pukul' HH:mm 'WIB'")
                    .withLocale(Locale.forLanguageTag("id-ID"))
                    .withZone(ZoneId.of("Asia/Jakarta"));

            String verificationLink = urlConfig.getBaseUrl() + "/verify?token=" + code;
            Context context = new Context();
            context.setVariable("name", nickName);
            context.setVariable("verificationLink", verificationLink);
            context.setVariable("expiredDate", formatter.format(expiredCode));
            String content = templateEngine.process("email-verification", context);

            helper.setFrom("no-reply@tiketinaja.com");
            helper.setTo(email);
            helper.setSubject("Verify your account - TiketInAja");
            helper.setText(content, true); // true = HTML

            javaMailSender.send(message);
            System.out.println("Verification email sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String resendCodeVerification(String email) {
        User userRequest = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found By Email"));
        userRequest.setVerificationCode(UUID.randomUUID().toString());
        userRequest.setVerificationCodeExpiresAt(Instant.now().plusSeconds(60)); // set selama 60 detik
        User userNewCode = userRepo.save(userRequest);
        sendEmailVerification(userNewCode.getEmail(), userNewCode.getNickName(),
                userNewCode.getVerificationCodeExpiresAt(), userNewCode.getVerificationCode());
        return userNewCode.getVerificationCode();
    }
}
