package com.mouady.repository;

import com.mouady.model.Aircraft;
import com.mouady.model.Airline;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    boolean existsByCode(@NotBlank(message = "Aircraft code is required") String code);

    List<Aircraft> findByAirline(Airline airline);
}
