package com.mouady.controller;

import com.mouady.payload.request.CityRequest;
import com.mouady.payload.response.ApiResponse;
import com.mouady.payload.response.CityResponse;
import com.mouady.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
public class CityController {
    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponse> createCity(
            @Valid @RequestBody CityRequest cityRequest
    ) {
        CityResponse cityResponse = cityService.createCity(cityRequest);
        return new ResponseEntity<>(cityResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable Long id){
        CityResponse cityResponse = cityService.getCityById(id);
        return new ResponseEntity<>(cityResponse, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @RequestParam(defaultValue = "0")  int page ,
            @RequestParam(defaultValue = "20" ) int size,
            @RequestParam(defaultValue = "name" ) String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(cityService.getAllCities(pageable)) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCityById(
            @PathVariable Long id ,
            @Valid @RequestBody CityRequest request
    ) {
        CityResponse cityResponse = cityService.updateCity(id, request);
        return new ResponseEntity<>(cityResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCityById(@PathVariable Long id)
    throws Exception {
        cityService.deleteCity(id);
        return ResponseEntity.ok(new ApiResponse("city deleted successfully"));

    }

    @GetMapping("/search")
    public ResponseEntity<Page<CityResponse>> searchCity(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0" ) int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cityService.searchCities(keyword, pageable));

    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(
            @PathVariable String countryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cityService.getCitiesByCountryCode(countryCode.toUpperCase(), pageable));
    }

    // ---------- VALIDATION ----------

    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<Boolean> checkCityExists(@PathVariable String cityCode) {
        return ResponseEntity.ok(cityService.cityExists(cityCode.toUpperCase()));
    }


}
