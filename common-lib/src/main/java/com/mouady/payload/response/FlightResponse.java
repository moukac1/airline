package com.mouady.payload.response;

import com.mouady.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightResponse {
    private Long id;

    private String flightNumber;

    private AirlineResponse airline;
    private AircraftResponse aircraft;

    private AirportResponse departureAirport;
    private AirportResponse arrivalAirport;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private Double lowestPrice;
    private Integer totalAvailableSeats;

    private FlightStatus status;

    private Instant createdAt;
    private Instant updatedAt;
}
