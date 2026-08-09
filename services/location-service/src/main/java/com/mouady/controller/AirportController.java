package com.mouady.controller;


import com.mouady.exception.AirportException;
import com.mouady.payload.request.AirportRequest;
import com.mouady.payload.response.AirportResponse;
import com.mouady.payload.response.ApiResponse;
import com.mouady.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportResponse> createAirport(
           @Valid @RequestBody AirportRequest airportRequest
    ) {
        AirportResponse airportResponse = airportService.createAirport(airportRequest);
        return new ResponseEntity<>(airportResponse, HttpStatus.CREATED);

    }
    @GetMapping
    public ResponseEntity<List<AirportResponse>> getAllAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());
    }


    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getAirportById(
            @PathVariable Long id
    ) {
        AirportResponse airportResponse = airportService.getAirportById(id);
        return new ResponseEntity<>(airportResponse, HttpStatus.OK);
    }

    @GetMapping("/city/{cityId}")
    public Object getAirportByCityId(
            @PathVariable Long cityId
    ){
        Object airportResponses = airportService.getAirportByCityId(cityId) ;
        return new ResponseEntity<>(airportResponses , HttpStatus.OK) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportResponse> updateAirport(
         @PathVariable  Long id , @Valid @RequestBody AirportRequest airportRequest
    ) throws AirportException {
        AirportResponse airportResponse = airportService.updateAirport(id, airportRequest);
        return new ResponseEntity<>(airportResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteAirport(
            @PathVariable Long id
    ){
        airportService.deleteAirport(id);
        return new ResponseEntity<>(new ApiResponse("Airport was successfully deleted"),  HttpStatus.NO_CONTENT);
    }



}
