package com.mouady.service;


import com.mouady.clients.AncillaryClient;
import com.mouady.exception.ResourceNotFoundException;
import com.mouady.model.Passenger;
import com.mouady.payload.request.PassengerRequest;
import com.mouady.payload.response.PassengerResponse;

public interface PassengerService {

    PassengerResponse createPassenger(PassengerRequest request, Long userId)
            throws ResourceNotFoundException;

    Passenger findOrCreatePassengerEntity(PassengerRequest request, Long userId);

    Passenger findExistingPassenger(PassengerRequest request);

    boolean existsById(Long id);

    long count();
}
