package com.mouady.payload.request;


import com.mouady.enums.FlightStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightRequest {

    @NotBlank(message = "flight number is required")
    @Size(max = 10)
    private String flightNumber;

    private Long airlineId;
    @NotNull(message = "aircraft id is required")
    private Long aircraftId;
    @NotNull(message = "departureAirportId id is required")
    private Long departureAirportId;
    @NotNull(message = "arrivalAirportId id is required")
    private Long arrivalAirportId;

    private FlightStatus status;





}
