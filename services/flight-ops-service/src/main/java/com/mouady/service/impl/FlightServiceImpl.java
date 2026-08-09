package com.mouady.service.impl;

import com.mouady.client.AirlineClient;
import com.mouady.client.LocationClient;
import com.mouady.enums.FlightStatus;
import com.mouady.mapper.FlightMapper;
import com.mouady.model.Flight;
import com.mouady.payload.request.FlightRequest;
import com.mouady.payload.response.AircraftResponse;
import com.mouady.payload.response.AirlineResponse;
import com.mouady.payload.response.AirportResponse;
import com.mouady.payload.response.FlightResponse;
import com.mouady.repository.FlightRepository;
import com.mouady.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirlineClient airlineClient;
    private final LocationClient locationClient ;
    @Override
    public FlightResponse createFlight(
            Long airlineId,
            FlightRequest flightRequest
    ) throws Exception {
        if(flightRepository.existsByFlightNumber(flightRequest.getFlightNumber())) {
            throw new Exception("already exists") ;
        }
        Flight flight = FlightMapper.toEntity(flightRequest);
        flight.setAirlineId(airlineId);
        Flight savedFlight = flightRepository.save(flight);
        return convertToFlightResponse(savedFlight);
    }

    @Override
    public Page<FlightResponse> getFlightsByAirlineId(
            Long airlineId,
            Long departureId, Long arrivalId, Pageable pageable
    ){

        return flightRepository.findByAirlineId(
                airlineId, departureId , arrivalId ,pageable)
                .map(this::convertToFlightResponse);
    }

    @Override
    public FlightResponse getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("flight not found with id: " + id));
        return convertToFlightResponse(flight);
    }

    @Override
    public FlightResponse updateFlightById(Long id, FlightRequest flightRequest) {

        Flight existing = flightRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("flight not found with id: " + id));

        if(flightRequest.getFlightNumber() != null &&
        flightRepository.existsByFlightNumberAndIdNot(flightRequest.getFlightNumber(), id)
        ) {
            throw new RuntimeException("already exists") ;
        }
        FlightMapper.updateEntity(flightRequest, existing);
        //Flight flight = FlightMapper.toEntity(existing);
        //System.out.println(flight);
        Flight savedFlight = flightRepository.save(existing);
        return convertToFlightResponse(savedFlight);
    }

    @Override
    public FlightResponse changeStatus(Long id, FlightStatus status) {
        Flight existing = flightRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("flight not found with id: " + id));
        existing.setStatus(status);
        Flight savedFlight = flightRepository.save(existing);
        return convertToFlightResponse(savedFlight);
    }

    @Override
    public void deleteFlightByAirlineIdAndId(Long airlineId , Long id) {
        Flight existing = flightRepository.findByAirlineIdAndId(airlineId, id)
                .orElseThrow(
                        () -> new RuntimeException("flight not found with id: " + id + " and airlineId: " + airlineId)
                );
        flightRepository.delete(existing);
    }

    public FlightResponse convertToFlightResponse(Flight flight) {
//              service to service communication


        AircraftResponse aircraftResponse = airlineClient.getAircraftById(
                flight.getAircraftId()
        );
        AirlineResponse airlineResponse = airlineClient.getAirlineById(
                flight.getAirlineId()
        );
        AirportResponse departureAirport=locationClient.getAirportById(
                flight.getDepartureAirportId()
        );
        AirportResponse arrivalAirport=locationClient.getAirportById(
                flight.getArrivalAirportId()
        );
        return FlightMapper.toResponse(
                flight,
                aircraftResponse,
                airlineResponse,
                departureAirport,
                arrivalAirport
        ) ;
    }
}
