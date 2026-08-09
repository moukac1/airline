package com.mouady.service;


import com.mouady.payload.request.FlightScheduleRequest;
import com.mouady.payload.response.FlightResponse;
import com.mouady.payload.response.FlightScheduleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface FlightScheduleService {

    FlightScheduleResponse createFlightSchedule(
            Long airlineId,
            FlightScheduleRequest request
    );

    FlightScheduleResponse getFlightScheduleById(Long id);
    List<FlightScheduleResponse> getAllFlightScheduleByAirline(
            Long airlineId
    );
    FlightScheduleResponse updateFlightSchedule(
            Long id,
            FlightScheduleRequest request
    );
    void deleteFlightScheduleById(Long id);



}
