package com.mouady.repository;

import com.mouady.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    boolean existsByIataCode(String iataCode);
    List<Airport> findByCityId(Long cityId);

    boolean existsByCityId(Long cityId);
}

