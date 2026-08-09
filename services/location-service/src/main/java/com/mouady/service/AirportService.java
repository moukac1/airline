package com.mouady.service;

import com.mouady.exception.AirportException;
import com.mouady.payload.request.AirportRequest;
import com.mouady.payload.response.AirportResponse;

import java.util.List;

public interface AirportService {

    AirportResponse createAirport(AirportRequest request);
    AirportResponse getAirportById(Long id);
    AirportResponse updateAirport(Long id , AirportRequest request) throws AirportException;
    List<AirportResponse> getAllAirports();
    void deleteAirport(Long id);
    Object getAirportByCityId(Long cityId);
}
