package com.mouady.service;

import com.mouady.model.FlightInstance;
import com.mouady.payload.request.FlightInstanceRequest;
import com.mouady.payload.response.ApiResponse;
import com.mouady.payload.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface FlightInstanceService {

    FlightInstanceResponse createFlightInstance(
            Long airlineId,
            FlightInstanceRequest request
    ) ;

    FlightInstanceResponse updateFlightInstance(
            Long id,
            FlightInstanceRequest request

    );
    FlightInstanceResponse getFlightInstanceById(
            Long id
    );
    Page<FlightInstanceResponse> getAllFlightInstancesByAirlineId(
            Long airlineId,
            Long departureAirportId,
            Long arrivalAirportId,
            Long flightId,
            LocalDate onDate,
            Pageable pageable
    );

    ResponseEntity<ApiResponse> deleteFlightInstanceById(
            Long id
    );
}
