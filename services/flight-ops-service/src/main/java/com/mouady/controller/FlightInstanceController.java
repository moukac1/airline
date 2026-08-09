package com.mouady.controller;

import com.mouady.model.FlightInstance;
import com.mouady.payload.request.FlightInstanceRequest;
import com.mouady.payload.response.FlightInstanceResponse;
import com.mouady.service.FlightInstanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/flight-instances")
@RequiredArgsConstructor
public class FlightInstanceController {
    private final FlightInstanceService flightInstanceService;

    @PostMapping
    public ResponseEntity<FlightInstanceResponse> createFlightInstance(
            @RequestHeader("X-User-Id") Long airlineId,
            @Valid @RequestBody FlightInstanceRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightInstanceService.createFlightInstance(
                        airlineId, request)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse> getFlightInstanceById(
            @PathVariable("id") Long id
    ) {

        return ResponseEntity.ok(
                flightInstanceService.getFlightInstanceById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<FlightInstanceResponse>> getFlightInstancesByAirlineId(
            @RequestHeader("X-User-Id") Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) LocalDate onDate,
            Pageable pageable
    ){
        return ResponseEntity.ok(
                flightInstanceService.getAllFlightInstancesByAirlineId(
                        airlineId,
                        departureAirportId,
                        arrivalAirportId,
                        flightId,
                        onDate,
                        pageable
                )
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse> updateFlightInstance(
            @PathVariable Long id,
            @Valid @RequestBody FlightInstanceRequest request
    ){
        return ResponseEntity.ok(
                flightInstanceService.updateFlightInstance(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlightInstance(
            @PathVariable Long id
    ){
        flightInstanceService.deleteFlightInstanceById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }















}
