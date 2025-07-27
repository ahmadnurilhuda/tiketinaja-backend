package com.greenacademy.tiketinaja.service;

import org.springframework.stereotype.Service;

import com.greenacademy.tiketinaja.exception.ResourceNotFoundException;
import com.greenacademy.tiketinaja.models.City;
import com.greenacademy.tiketinaja.repositories.CityRepository;

@Service
public class CityService {

    private final CityRepository cityRepo;
    public CityService(CityRepository cityRepo) {
        this.cityRepo = cityRepo;
    }

    public City get(Integer id) {
        return cityRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("City not found"));
    }

    public Iterable<City> getAll() {
        return cityRepo.findAll();
    }
    
}
