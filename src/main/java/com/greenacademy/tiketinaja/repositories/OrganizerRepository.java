package com.greenacademy.tiketinaja.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.Organizer;

public interface OrganizerRepository extends CrudRepository<Organizer, Integer> {

    Optional<Organizer> findByUserId(Integer userId);
    
}
