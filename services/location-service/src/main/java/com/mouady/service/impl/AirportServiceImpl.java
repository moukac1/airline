package com.mouady.service.impl;

import com.mouady.exception.AirportException;
import com.mouady.exception.CityException;
import com.mouady.mapper.AirportMapper;
import com.mouady.model.Airport;
import com.mouady.model.City;
import com.mouady.payload.request.AirportRequest;
import com.mouady.payload.response.AirportResponse;
import com.mouady.payload.response.ApiResponse;
import com.mouady.repository.AirportRepository;
import com.mouady.repository.CityRepository;
import com.mouady.service.AirportService;
import com.mouady.service.CityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;
    @Transactional
    @Override
    public AirportResponse createAirport(AirportRequest request) {
        if(airportRepository.existsByIataCode(request.getIataCode())){
            throw new RuntimeException("airport is already here this id") ;
        }
        City city = cityRepository.findById(request.getCityId()).orElseThrow(
                () -> new RuntimeException("city not found"));

        Airport airport = AirportMapper.toEntity(request);
        airport.setCity(city);
        Airport savedAirport = airportRepository.save(airport);
        return AirportMapper.toResponse(savedAirport);

    }
    @Transactional(readOnly = true)
    @Override
    @Cacheable(cacheNames = "airports", key = "#id")
    public AirportResponse getAirportById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Airport not found with id: " + id));
        return AirportMapper.toResponse(airport);
    }

    @Transactional(readOnly = true)
    @Override
    @Cacheable(cacheNames = "allAirports")

    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "airports", key = "#id"),
            @CacheEvict(cacheNames = "allAirports", allEntries = true),
            @CacheEvict(cacheNames = "airportsByIata", allEntries = true),
            @CacheEvict(cacheNames = "airportsByCity", allEntries = true)
    })
    public AirportResponse updateAirport(Long id, AirportRequest request) throws AirportException {
        Airport existingAirport = airportRepository.findById(id)
                .orElseThrow(() -> new AirportException("Airport not found with id: " + id));

        if (request.getIataCode() != null
                && !existingAirport.getIataCode().equals(request.getIataCode())
                && airportRepository.existsByIataCode(request.getIataCode())) {
            throw new AirportException("IATA code " + request.getIataCode() + " is already taken.");
        }

        if (request.getCityId() != null) {
            City newCity = cityRepository.findById(request.getCityId())
                    .orElseThrow(() -> new CityException("City not found with id: " + request.getCityId()));
            existingAirport.setCity(newCity);
        }

        AirportMapper.updateEntity(request, existingAirport);

        Airport updatedAirport = airportRepository.save(existingAirport);
        return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "airports", key = "#id"),
            @CacheEvict(cacheNames = "allAirports", allEntries = true),
            @CacheEvict(cacheNames = "airportsByIata", allEntries = true),
            @CacheEvict(cacheNames = "airportsByCity", allEntries = true)
    })
    public void deleteAirport(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Airport not found with id: " + id));
        airportRepository.delete(airport);
    }

    @Transactional(readOnly = true)
    @Override
    @Cacheable(cacheNames = "airportsByCity", key = "#cityId")
    public Object getAirportByCityId(Long cityId) {
        if(!airportRepository.existsByCityId(cityId)){
            return new ResponseEntity<>(new ApiResponse("the city id given doesn't exist"),HttpStatus.NOT_FOUND);
        }
        return  airportRepository.findByCityId(cityId)
                .stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }
}
