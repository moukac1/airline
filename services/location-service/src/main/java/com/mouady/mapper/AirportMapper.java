package com.mouady.mapper;


import com.mouady.model.Airport;
import com.mouady.payload.request.AirportRequest;
import com.mouady.payload.response.AirportResponse;

import java.time.ZoneId;

public class AirportMapper {
    public static Airport toEntity(AirportRequest request) {
        if (request == null) return null;

        return Airport.builder()
                .iataCode(request.getIataCode())
                .name(request.getName())
                .timeZoneId(request.getTimeZone() != null ?  request.getTimeZone() : null)
                .address(request.getAddress())
                .geoCode(request.getGeoCode())
                .build();
    }

    public static AirportResponse toResponse(Airport airport) {
        if (airport == null) return null;

        return AirportResponse.builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .name(airport.getName())
                .detailedName(airport.getDetailedName())
                .timeZone(airport.getTimeZoneId())
                .address(airport.getAddress())
                .city(CityMapper.toCityResponse(airport.getCity()))
                .geoCode(airport.getGeoCode())
                //.analytics(airport.getAnalytics())
                .build();
    }

    public static void updateEntity(AirportRequest request, Airport existingAirport) {
        if (request == null || existingAirport == null) return;

        if (request.getIataCode() != null) {
            existingAirport.setIataCode(request.getIataCode());
        }
        if (request.getName() != null) {
            existingAirport.setName(request.getName());
        }
        if (request.getTimeZone() != null) {
            existingAirport.setTimeZoneId(request.getTimeZone());
        }
        if (request.getAddress() != null) {
            existingAirport.setAddress(request.getAddress());
        }
        if (request.getGeoCode() != null) {
            existingAirport.setGeoCode(request.getGeoCode());
        }
    }
}
