package com.mouady.client;

import com.mouady.exception.AirportException;
import com.mouady.payload.response.AirportResponse;
import org.springframework.stereotype.Component;

@Component
public class LocationClientFallback implements LocationClient {

    @Override
    public AirportResponse getAirportById(Long id) throws AirportException {
        return null;
    }
}
