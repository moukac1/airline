package com.mouady.mapper;


import com.mouady.model.Flight;
import com.mouady.model.FlightSchedule;
import com.mouady.payload.request.FlightScheduleRequest;
import com.mouady.payload.response.AirportResponse;
import com.mouady.payload.response.FlightScheduleResponse;

public class FlightScheduleMapper {

    public static FlightSchedule toEntity(FlightScheduleRequest request,
                                          Flight flight)
    {
        if(request == null || flight == null) return null;

        return FlightSchedule.builder()
                .flight(flight)
                .departureAirportId(flight.getDepartureAirportId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .operatingDays(request.getOperatingDays())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate() )
                .build();
    }

    public static FlightScheduleResponse toResponse(
            FlightSchedule fs,
            AirportResponse departure,
            AirportResponse arrival
    ) {

        if(fs == null ) return null;
        return FlightScheduleResponse.builder()
                .id(fs.getId())
                .flightId(fs.getId() == null ? null : fs.getId())
                .flightNumber(fs.getFlight() != null ? fs.getFlight().getFlightNumber() : null)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureTime(fs.getDepartureTime())
                .arrivalTime(fs.getArrivalTime())
                .startDate(fs.getStartDate())
                .endDate(fs.getEndDate())
                .isActive(fs.getIsActive())
                .operatingDays(fs.getOperatingDays())
                .build();
    }

    public static FlightSchedule updateEntity(
            FlightScheduleRequest request , FlightSchedule existing
    ){
        if(request == null || existing == null) return null;
        if(request.getDepartureTime() != null) existing.setDepartureTime(request.getDepartureTime());
        if(request.getArrivalTime() != null) existing.setArrivalTime(request.getArrivalTime());
        if(request.getStartDate() != null) existing.setStartDate(request.getStartDate());
        if(request.getEndDate() != null) existing.setEndDate(request.getEndDate());
        if(request.getIsActive() != null) existing.setIsActive(request.getIsActive());
        if(request.getOperatingDays() != null) existing.setOperatingDays(request.getOperatingDays());

        return existing ;


    }
}
