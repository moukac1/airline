package com.mouady.service.impl;


import com.mouady.client.AirlineClient;
import com.mouady.client.LocationClient;
import com.mouady.event.FlightInstanceCreatedEvent;
import com.mouady.event.FlightInstanceEventProducer;
import com.mouady.exception.AirportException;
import com.mouady.mapper.FlightInstanceMapper;
import com.mouady.mapper.FlightMapper;
import com.mouady.model.Flight;
import com.mouady.model.FlightInstance;
import com.mouady.payload.request.FlightInstanceRequest;
import com.mouady.payload.response.*;
import com.mouady.repository.FlightInstanceRepository;
import com.mouady.repository.FlightRepository;
import com.mouady.service.FlightInstanceService;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {

    private final FlightInstanceRepository flightInstanceRepository;
    private final FlightRepository flightRepository;
    private final AirlineClient airlineClient;
    private final LocationClient locationClient;

    private final FlightInstanceEventProducer flightInstanceEventProducer;

    @Override
    public FlightInstanceResponse createFlightInstance(
            Long userId,
            FlightInstanceRequest request
    ) {
        Flight flight = flightRepository.findById(
                request.getFlightId()
        ).orElseThrow(
                ()-> new RuntimeException("flight not found")
        );
        Long airlineId = getAirlineForUser(userId);

        AircraftResponse aircraftResponse =  getAircraftById(flight.getAircraftId());
        FlightInstance flightInstance = FlightInstanceMapper.toEntity(
                request, flight
        );
        flightInstance.setFlight(flight);
        flightInstance.setDepartureAirportId(request.getDepartureAirportId());
        flightInstance.setArrivalAirportId(request.getArrivalAirportId());
        flightInstance.setAirlineId(airlineId);
        flightInstance.setTotalSeats(aircraftResponse.getTotalSeats());
        flightInstance.setAvailableSeats(aircraftResponse.getTotalSeats());
        FlightInstance saved =  flightInstanceRepository.save(flightInstance) ;
        flightInstanceEventProducer.sendFlightInstanceCreated(
                FlightInstanceCreatedEvent.builder()
                        .flightInstanceId(flightInstance.getId())
                        .aircraftId(flight.getAircraftId())
                        .flightId(flight.getId())
                        .build()
        );
        return convertToFlightInstanceResponse(saved) ;
    }

    @Override
    public FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) {
        FlightInstance existing = flightInstanceRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("flight not found with id: " + id)
        ) ;

        FlightInstance saved =  flightInstanceRepository.save(FlightInstanceMapper.updateEntity(request , existing )) ;
        return convertToFlightInstanceResponse(saved) ;
    }

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) {
        FlightInstance existing = flightInstanceRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("flight not found with id: " + id)
        ) ;

        return convertToFlightInstanceResponse(existing);
    }

    @Override
    public Page<FlightInstanceResponse> getAllFlightInstancesByAirlineId(
            Long userId,
            Long departureAirportId,
            Long arrivalAirportId,
            Long flightId,
            LocalDate onDate,
            Pageable pageable
    ) {
        LocalDateTime start = onDate != null ? onDate.atStartOfDay() : null;
        LocalDateTime end = onDate != null ? onDate.plusDays(1).atStartOfDay(): null;

        return flightInstanceRepository.findByAirlineId(
                userId,
                departureAirportId,
                arrivalAirportId,
                flightId,
                start,
                end,
                pageable
                ).map(
                        this::convertToFlightInstanceResponse
        );

    }

    @Override
    public ResponseEntity<ApiResponse>  deleteFlightInstanceById(Long id) {
        FlightInstance existing = flightInstanceRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("flight not found with id: " + id)

        );
        flightInstanceRepository.delete(existing);
        return new ResponseEntity<>(new ApiResponse("FlightInstance was successfully deleted"),  HttpStatus.NO_CONTENT);

    }

    private FlightInstanceResponse convertToFlightInstanceResponse(FlightInstance flightInstance) {

//              service to service communication

        AirlineResponse airlineResponse = airlineClient.getAirlineById(flightInstance.getAirlineId());
        AirportResponse departureAirport = locationClient.getAirportById(flightInstance.getDepartureAirportId());
        AirportResponse arrivalAirport = locationClient.getAirportById(flightInstance.getArrivalAirportId());
        AircraftResponse aircraftResponse = airlineClient.getAircraftById(flightInstance.getFlight().getAircraftId());
        return FlightInstanceMapper.toResponse(
                flightInstance,
                aircraftResponse,
                airlineResponse,
                departureAirport,
                arrivalAirport
        ) ;
    }
    private AircraftResponse getAircraftById(Long aircraftId) {
        try {
            return airlineClient.getAircraftById(aircraftId);
        } catch (FeignException.NotFound e) {
            throw new EntityNotFoundException("No aircraft found for id: " + aircraftId);
        } catch (FeignException e) {
            throw new RuntimeException(
                    "Failed to fetch aircraft from airline-core-service: " + e.getMessage(), e);
        }
    }

    private Long getAirlineForUser(Long userId) {
        try {
            AirlineResponse airline = airlineClient.getAirlineByOwner(userId);
            return airline.getId();
        } catch (FeignException.NotFound e) {
            throw new EntityNotFoundException("No airline found for user: " + userId);
        } catch (FeignException e) {
            throw new RuntimeException(
                    "Failed to fetch airline from airline-core-service: " + e.getMessage(), e);
        }
    }


}
