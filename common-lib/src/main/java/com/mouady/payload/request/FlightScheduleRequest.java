package com.mouady.payload.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightScheduleRequest {

    @NotNull(message = "flight id is required")
    private Long flightId;

    @NotNull(message = "departure time is required")
    private LocalTime departureTime;
    @NotNull(message = "arrival time is required")
    private LocalTime arrivalTime;

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    private List<DayOfWeek>  operatingDays;
    @NotNull
    private Boolean isActive ;









}
