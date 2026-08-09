package com.mouady.service;

import com.mouady.exception.ResourceNotFoundException;
import com.mouady.payload.request.AircraftRequest;
import com.mouady.payload.response.AircraftResponse;

import java.util.List;

public interface AircraftService {

    AircraftResponse getAircraftById(Long id) throws ResourceNotFoundException;

    List<AircraftResponse> listAllAircraftsByOwner(Long ownerId);

    AircraftResponse createAircraft(
            AircraftRequest request,
            Long ownerId
    ) throws ResourceNotFoundException;

    AircraftResponse updateAircraft(
            Long id,
            AircraftRequest request,
            Long ownerId
    ) throws ResourceNotFoundException;

    void deleteAircraft(Long id) throws ResourceNotFoundException;
}