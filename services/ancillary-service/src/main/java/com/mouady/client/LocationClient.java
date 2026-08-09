package com.mouady.client;

import com.mouady.exception.AirportException;
import com.mouady.payload.response.AirportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "location-service", fallback = LocationClientFallback.class)
public interface LocationClient {

    @GetMapping("/{id}")
    AirportResponse getAirportById(@PathVariable Long id) throws AirportException;
}
