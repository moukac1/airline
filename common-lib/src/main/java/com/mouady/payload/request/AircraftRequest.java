package com.mouady.payload.request;


import com.mouady.enums.AircraftStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AircraftRequest {

    @NotBlank(message = "aircraft code is required")
    private String code;

    @NotBlank(message = "aircraft model code is required")
    private String model;
    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;


    @NotNull(message = "Seating capacity is required")
    @Positive(message = "Seating capacity must be positive")
    private Integer seatingCapacity;

    @Positive(message = "Seating capacity must be positive")
    private Integer economySeats;
    @Positive(message = "Seating capacity must be positive")
    private Integer premiumEconomySeats;

    @Positive(message = "Seating capacity must be positive")
    private Integer businessSeats;
    @Positive(message = "Seating capacity must be positive")
    private Integer firstClassSeats;

    @Positive(message = "Range must be positive")
    private Integer rangeKm;

    @Positive(message = "crui must be positive")
    private Integer cruisingSpeedKmh;

    @Positive
    private Integer maxAltitudeFt;

    private Integer yearOfManufacture;
    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;
    private AircraftStatus status ;
    private Boolean isAvailable ;
    private Long currentAirportId;





}
