package com.mouady.mapper;


import com.mouady.enums.FlightStatus;
import com.mouady.model.Flight;
import com.mouady.model.FlightInstance;
import com.mouady.payload.request.FlightInstanceRequest;
import com.mouady.payload.response.AircraftResponse;
import com.mouady.payload.response.AirlineResponse;
import com.mouady.payload.response.AirportResponse;
import com.mouady.payload.response.FlightInstanceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
@Builder
@RequiredArgsConstructor
public class FlightInstanceMapper {

    public static FlightInstance toEntity(
            FlightInstanceRequest request,
            Flight flight
    ) {
        if(flight == null) {return null;}
        return FlightInstance.builder()
                .flight(flight)
                .airlineId(flight.getAirlineId())
                .scheduleId(request.getScheduleId())
                .departureAirportId(
                        request.getDepartureAirportId() != null ?
                                request.getDepartureAirportId() : null
                )
                .arrivalAirportId(
                        request.getArrivalAirportId() != null ?
                                request.getArrivalAirportId() : null
                )
                .departureDateTime(request.getDepartureDateTime())
                .arrivalDateTime(request.getArrivalDateTime())
                .status(FlightStatus.SCHEDULED)
                .minAdvanceBookingDays(request.getMinAdvanceBookingDys())
                .maxAdvanceBookingDays(request.getMaxAdvanceBookingDys())
                .isActive(request.getIsActive() != null ?
                        request.getIsActive() : null
                )
                .build();

    }
    public static FlightInstanceResponse toResponse (
            FlightInstance fi,
            AircraftResponse aircraftResponse, AirlineResponse airline,
            AirportResponse departureAirport,
            AirportResponse arrivalAirport
    ) {

        if (fi == null) return null;
        return FlightInstanceResponse.builder()
                .id(fi.getId())
                .flightId(fi.getFlight() != null? fi.getFlight().getId() : null)
                .flightNumber(fi.getFlight()!= null? fi.getFlight().getFlightNumber(): null)
                .aircraftId(fi.getFlight() .getAircraftId())
                .aircraftModal(aircraftResponse.getModel())
                .aircraftCode(aircraftResponse.getCode())
                .airlineId(fi.getAirlineId())
                .airlineName(airline.getName())
                .airlineLogo(airline.getLogoUrl())
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .formattedDuration(fi.getFormatedDuration())
                .totalSeats(fi.getTotalSeats())
                .availableSeats(fi.getAvailableSeats())
                .status(fi.getStatus())
                .minAdvanceBookingDys(fi.getMinAdvanceBookingDays())
                .maxAdvanceBookingDys(fi.getMaxAdvanceBookingDays())
                .isActive(fi.getIsActive())
                .build() ;

    }
    public static FlightInstance updateEntity(
            FlightInstanceRequest request,
            FlightInstance existing
    ){
        if(request ==null || existing == null) {return null;}
        if(request.getDepartureAirportId()!=null) existing.setDepartureAirportId(request.getDepartureAirportId());
        if(request.getArrivalAirportId()!=null) existing.setArrivalAirportId(request.getArrivalAirportId());
        if(request.getTotalSeats()!=null) existing.setTotalSeats(request.getTotalSeats());
        if(request.getAvailableSeats()!=null) existing.setAvailableSeats(request.getAvailableSeats());
        if(request.getStatus()!=null) existing.setStatus(request.getStatus());
        if(request.getMaxAdvanceBookingDys()!=null) existing.setMaxAdvanceBookingDays(request.getMaxAdvanceBookingDys());
        if(request.getMinAdvanceBookingDys()!=null) existing.setMinAdvanceBookingDays(request.getMinAdvanceBookingDys());
        if(request.getIsActive()!=null) existing.setIsActive(request.getIsActive());
        return existing;

    }









}
