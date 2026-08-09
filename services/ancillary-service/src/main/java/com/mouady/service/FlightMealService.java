package com.mouady.service;



import com.mouady.exception.ResourceNotFoundException;
import com.mouady.payload.request.FlightMealRequest;
import com.mouady.payload.response.FlightMealResponse;

import java.util.List;

public interface FlightMealService {

    FlightMealResponse create(FlightMealRequest request) throws ResourceNotFoundException;

    List<FlightMealResponse> bulkCreate(List<FlightMealRequest> requests) throws ResourceNotFoundException;

    FlightMealResponse getById(Long id) throws ResourceNotFoundException;

    List<FlightMealResponse> getByFlightId(Long flightId);

    List<FlightMealResponse> getAllByIds(List<Long> Ids);

    FlightMealResponse update(Long id, FlightMealRequest request) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

    FlightMealResponse updateAvailability(Long id, Boolean available) throws ResourceNotFoundException;

    Double calculateMealPrice(List<Long> mealIds);
}
