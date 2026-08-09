package com.mouady.service;

import com.mouady.enums.FlightStatus;
import com.mouady.payload.request.FlightRequest;
import com.mouady.payload.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlightService {

    FlightResponse createFlight(Long airlineId ,   FlightRequest flightRequest) throws Exception;
    Page<FlightResponse> getFlightsByAirlineId(
            Long airlineId,
            Long departureId,
            Long arrivalId,
            Pageable pageable
    );

    FlightResponse getFlightById(Long id);
    FlightResponse updateFlightById(Long id, FlightRequest flightRequest);
    FlightResponse changeStatus(Long id, FlightStatus status );
    void deleteFlightByAirlineIdAndId(Long airlineId , Long id);

}
