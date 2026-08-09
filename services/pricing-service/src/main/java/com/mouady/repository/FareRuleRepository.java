package com.mouady.repository;

import com.mouady.model.FareRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FareRuleRepository extends JpaRepository<FareRule, Long> {
    Optional<FareRule> findByFareId(Long fareId);
    List<FareRule> findByAirlineId(Long airlineId);
    boolean existsByFareId(Long fareId);
}
