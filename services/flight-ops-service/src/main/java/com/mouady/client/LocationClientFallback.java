package com.mouady.client;

import com.mouady.payload.response.AirportResponse;
import org.springframework.stereotype.Component;

@Component
public class LocationClientFallback implements LocationClient {

    @Override
    public AirportResponse getAirportById(Long id) {
        return null;
    }
}
