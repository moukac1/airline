package com.mouady.payload.response;

import com.mouady.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightInstanceResponse {

    private Long id;
    private String terminal;
    private String gate ;
    private Long flightId;
    private String flightNumber;
    private Long airlineId;
    private String airlineName;
    private String airlineLogo ;
    private Long aircraftId ;
    private String aircraftModal ;
    private String aircraftCode ;
    private AirportResponse departureAirport ;
    private AirportResponse arrivalAirport ;
    private LocalDateTime departureDateTime ;
    private LocalDateTime arrivalDateTime ;
    private String formattedDuration ;
    private Integer totalSeats ;
    private Integer availableSeats ;
    private FlightStatus status ;
    private Integer minAdvanceBookingDys ;
    private Integer maxAdvanceBookingDys ;
    private Boolean isActive;
    private Long fare ;

    //private FareResponse fare ;
}
