package com.mouady.payload.request;


import com.mouady.enums.FlightStatus;
import com.mouady.payload.response.AirportResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightInstanceRequest {

    @NotNull(message = "flightId is required")
    private Long flightId;
    @NotNull
    private Long scheduleId;

    private Long departureAirportId;
    private Long arrivalAirportId;

   //@NotNull(message = "departure time is required")
    private LocalDateTime departureDateTime ;
    //@NotNull(message = "arrival time is required")
    private LocalDateTime arrivalDateTime ;

    //@NotNull
    @Positive
    private Integer totalSeats ;
    @PositiveOrZero
    private Integer availableSeats ;
    private FlightStatus status ;
    private Integer minAdvanceBookingDys ;
    private Integer maxAdvanceBookingDys ;
    private Boolean isActive;
}
