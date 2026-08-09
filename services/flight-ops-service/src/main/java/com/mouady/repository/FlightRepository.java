package com.mouady.repository;

import com.mouady.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
    select f from Flight f
    where f.airlineId=:airlineId
    and (:departureId is null or f.departureAirportId=:departureId)
    and(:arrivalId is null or f.arrivalAirportId=:arrivalId)
""")
    Page<Flight> findByAirlineId(@Param("airlineId") Long airlineId,
                                 @Param("departureId") Long departureId,
                                 @Param("arrivalId") Long arrivalId,
                                 Pageable pageable);

    Boolean existsByAirlineId(Long airlineId);
    Boolean existsByFlightNumberAndIdNot(String flightNumber, Long id);
    Boolean existsByFlightNumber(String flightNumber);

    Optional<Flight> findByAirlineIdAndId(Long airlineId, Long id);


    //Page<Flight> findByAirlineId(Long airlineId);
}
