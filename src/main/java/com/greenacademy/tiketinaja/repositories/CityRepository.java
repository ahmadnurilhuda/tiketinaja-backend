package com.greenacademy.tiketinaja.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.greenacademy.tiketinaja.models.City;

public interface CityRepository extends CrudRepository<City, Integer> {
    Optional<City> findById(Integer id);
}
