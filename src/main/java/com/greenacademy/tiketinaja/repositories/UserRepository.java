package com.greenacademy.tiketinaja.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional <User> findByEmail(String email);
    Optional<User> findByVerificationCode(String verificationCode);
    
}
