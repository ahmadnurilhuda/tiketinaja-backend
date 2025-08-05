package com.greenacademy.tiketinaja.service;

import org.springframework.stereotype.Service;

import com.greenacademy.tiketinaja.models.Province;
import com.greenacademy.tiketinaja.repositories.ProvinceRepository;

@Service
public class ProvinceService {

    private final ProvinceRepository provinceRepo;

    public ProvinceService(ProvinceRepository provinceRepo) {
        this.provinceRepo = provinceRepo;
    }

    public Iterable<Province> getAll() {
        return provinceRepo.findAll();
    }
}
