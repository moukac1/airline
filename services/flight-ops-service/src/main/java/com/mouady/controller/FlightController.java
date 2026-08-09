package com.mouady.controller;

import com.mouady.enums.FlightStatus;
import com.mouady.model.Flight;
import com.mouady.payload.request.FlightRequest;
import com.mouady.payload.response.FlightResponse;
import com.mouady.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(
            @RequestHeader("X-User-Id") Long airlineId,
            @Valid @RequestBody FlightRequest flightRequest) throws Exception {
        return ResponseEntity.ok(flightService.createFlight(airlineId , flightRequest) ) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse>  getFlight(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok( flightService.getFlightById(id) );
    }
    @GetMapping("/airline")
    public ResponseEntity<Page<FlightResponse>>  getFlightByAirlineId(
            @RequestHeader("X-User-Id") Long airlineId ,
            @RequestParam(required = false) Long departureId,
            @RequestParam(required = false) Long arrivalId,
            Pageable pageable
    ){

        return ResponseEntity.ok(
                flightService.getFlightsByAirlineId(
                airlineId, departureId, arrivalId , pageable)
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse>  updateFlight(
            @PathVariable Long id,
             @RequestBody FlightRequest flightRequest) throws Exception {
        return ResponseEntity.ok(flightService.updateFlightById(id, flightRequest))  ;
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<FlightResponse>  changeStatus(
            @PathVariable("id") Long id ,@RequestParam FlightStatus status
    ) throws Exception {
        return ResponseEntity.ok(flightService.changeStatus(id, status)) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(
            @RequestHeader("X-User-Id") Long airlineId,
            @PathVariable Long id
    )  {
        flightService.deleteFlightByAirlineIdAndId( airlineId , id ) ;
        return ResponseEntity.noContent().build();

    }


}
