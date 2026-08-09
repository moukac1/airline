package com.mouady.service.impl;


import com.mouady.client.LocationClient;
import com.mouady.enums.FlightStatus;
import com.mouady.mapper.FlightInstanceMapper;
import com.mouady.mapper.FlightScheduleMapper;
import com.mouady.model.Flight;
import com.mouady.model.FlightSchedule;
import com.mouady.payload.request.FlightInstanceRequest;
import com.mouady.payload.request.FlightScheduleRequest;
import com.mouady.payload.response.AirportResponse;
import com.mouady.payload.response.FlightResponse;
import com.mouady.payload.response.FlightScheduleResponse;
import com.mouady.repository.FlightInstanceRepository;
import com.mouady.repository.FlightRepository;
import com.mouady.repository.FlightScheduleRepository;
import com.mouady.service.FlightInstanceService;
import com.mouady.service.FlightScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class FlightScheduleServiceImpl implements FlightScheduleService {
    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightRepository flightRepository;
    private final FlightInstanceService flightInstanceService;
    private final LocationClient locationClient;
    @Override
    public FlightScheduleResponse createFlightSchedule(
            Long airlineId,
            FlightScheduleRequest request
    ) {
        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow(
                () -> new RuntimeException("Flight not found")
        );
        if(request.getEndDate().isBefore(request.getStartDate())) {
            throw new RuntimeException("Start date cannot be before end date");
        }

        FlightSchedule flightSchedule = FlightScheduleMapper.toEntity(
                request,flight
        );
        FlightSchedule saved =flightScheduleRepository.save(flightSchedule);
        List<DayOfWeek> operatingDays = saved.getOperatingDays();
        LocalDate startDate = saved.getStartDate();
        LocalDate endDate = saved.getEndDate();
        FlightInstanceRequest flightInstanceRequest =
                FlightInstanceRequest.builder()
                        .scheduleId(saved.getId())
                        .flightId(flight.getId())
                        .arrivalAirportId(flight.getArrivalAirportId())
                        .departureAirportId(flight.getDepartureAirportId())
                        .status(FlightStatus.SCHEDULED)
                        .build();
        for(LocalDate date=startDate; !date.isAfter(endDate); date=date.plusDays(1)) {
            if(operatingDays.contains(date.getDayOfWeek())) {
                flightInstanceRequest.setDepartureDateTime(
                        LocalDateTime.of(date, saved.getDepartureTime())
                );
                flightInstanceRequest.setArrivalDateTime(
                        LocalDateTime.of(date, saved.getArrivalTime())
                );
                flightInstanceService.createFlightInstance(
                        airlineId,
                        flightInstanceRequest

                );
            }

        }
        return convertToFlightScheduleResponse(saved);




    }

    @Override
    public FlightScheduleResponse getFlightScheduleById(Long id) {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Flight not found")
        );
        return convertToFlightScheduleResponse(flightSchedule);
    }

    @Override
    public List<FlightScheduleResponse> getAllFlightScheduleByAirline(Long airlineId) {
        List<FlightSchedule> schedules =
                flightScheduleRepository.findByFlightAirlineId(airlineId);

        return schedules.stream()
                .map(this::convertToFlightScheduleResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest request) {

        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Flight not found")
        );
         FlightSchedule flightS =  FlightScheduleMapper.updateEntity(request, flightSchedule) ;
         FlightSchedule fs =  flightScheduleRepository.save(flightS);

         return convertToFlightScheduleResponse(fs);

    }

    @Override
    public void deleteFlightScheduleById(Long id) {
            FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Flight not found")
            );
            flightScheduleRepository.delete(flightSchedule);
    }

    private FlightScheduleResponse convertToFlightScheduleResponse(FlightSchedule flightSchedule) {
        AirportResponse arrivalAirport=locationClient.getAirportById(flightSchedule.getArrivalAirportId());
        AirportResponse departureAirport=locationClient.getAirportById(flightSchedule.getDepartureAirportId());
        return FlightScheduleMapper.toResponse(
                flightSchedule, arrivalAirport , departureAirport
        ) ;
    }
}
