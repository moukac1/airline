package com.mouady.mapper;


import com.mouady.model.Flight;
import com.mouady.payload.request.FlightRequest;
import com.mouady.payload.response.AircraftResponse;
import com.mouady.payload.response.AirlineResponse;
import com.mouady.payload.response.AirportResponse;
import com.mouady.payload.response.FlightResponse;

import lombok.Builder;
import lombok.RequiredArgsConstructor;



@Builder
@RequiredArgsConstructor
public class FlightMapper {

    public static Flight toEntity(FlightRequest request){
        return Flight.builder()
                .flightNumber(request.getFlightNumber())
                .airlineId(request.getAirlineId())
                .aircraftId(request.getAircraftId())
                .departureAirportId(request.getDepartureAirportId())
                .arrivalAirportId(request.getArrivalAirportId())
                .status(request.getStatus())
                .build();
    }

    public static FlightResponse toResponse(
            Flight flight,
            AircraftResponse  aircraftResponse,
            AirlineResponse airlineResponse,
            AirportResponse departureAirport,
            AirportResponse arrivalAirport ){

        if(flight == null){return null;}
        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(airlineResponse)
                .aircraft(aircraftResponse)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .status(flight.getStatus())
                //.departureTime()
                .createdAt(flight.getCreatedAt())
                .updatedAt(flight.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FlightRequest request, Flight existing){
        if(request==null || existing==null){return;}
        if(request.getFlightNumber() != null) existing.setFlightNumber(request.getFlightNumber());
        if(request.getAirlineId() != null) existing.setAirlineId(request.getAirlineId());
        if(request.getAircraftId() != null) existing.setAircraftId(request.getAircraftId());
        if(request.getDepartureAirportId() != null) existing.setDepartureAirportId(request.getDepartureAirportId()) ;
        if(request.getArrivalAirportId() != null) existing.setArrivalAirportId(request.getArrivalAirportId()) ;
        if(request.getStatus() != null) existing.setStatus(request.getStatus());
    }


}
