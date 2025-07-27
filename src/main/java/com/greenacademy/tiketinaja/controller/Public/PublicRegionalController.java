package com.greenacademy.tiketinaja.controller.Public;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenacademy.tiketinaja.common.ApiResponse;
import com.greenacademy.tiketinaja.models.City;
import com.greenacademy.tiketinaja.models.Province;
import com.greenacademy.tiketinaja.service.CityService;
import com.greenacademy.tiketinaja.service.ProvinceService;

@RestController
public class PublicRegionalController {

    private final CityService cityService;
    private final ProvinceService provinceService;

    public PublicRegionalController(CityService cityService, ProvinceService provinceService) {
        this.cityService = cityService;
        this.provinceService = provinceService;
    }

    @GetMapping("/public/regional/cities")
    public ResponseEntity<ApiResponse<Iterable<City>>> getCities() {
        return ResponseEntity.ok(new ApiResponse<Iterable<City>>(true, "Success Get All Cities", cityService.getAll()));
    }

    @GetMapping("/public/regional/provinces")
    public ResponseEntity<ApiResponse<Iterable<Province>>> getProvinces() {
        return ResponseEntity.ok(new ApiResponse<Iterable<Province>>(true, "Success Get All Provinces", provinceService.getAll()));
    }
    
}
