package com.mouady.controller;

import com.mouady.payload.request.FlightScheduleRequest;
import com.mouady.payload.response.FlightScheduleResponse;
import com.mouady.service.FlightScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight-schedules")
@RequiredArgsConstructor
public class FlightScheduleController {
    private final FlightScheduleService flightScheduleService;


    @PostMapping
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @RequestHeader("X-User-Id") Long airlineId,
            @Valid @RequestBody FlightScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        flightScheduleService.createFlightSchedule(airlineId, request)
        );
    }

    @GetMapping("/{id}")

    public ResponseEntity<FlightScheduleResponse> getFlightSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(
                flightScheduleService.getFlightScheduleById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<FlightScheduleResponse>> getFlightSchedules(
            @RequestHeader("X-User-Id") Long airlineId
    ) {
        return ResponseEntity.ok(
                flightScheduleService.getAllFlightScheduleByAirline(airlineId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
            @PathVariable Long id,
            @Valid @RequestBody FlightScheduleRequest request
    ){
        return ResponseEntity.ok(flightScheduleService.updateFlightSchedule(
                id, request
        ));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlightSchedule(@PathVariable Long id) {
        flightScheduleService.deleteFlightScheduleById(id);
        return ResponseEntity.noContent().build();
    }

}
