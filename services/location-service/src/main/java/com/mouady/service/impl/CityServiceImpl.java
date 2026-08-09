package com.mouady.service.impl;


import com.mouady.mapper.CityMapper;
import com.mouady.model.City;
import com.mouady.payload.request.CityRequest;
import com.mouady.payload.response.CityResponse;
import com.mouady.repository.CityRepository;
import com.mouady.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest request) {
        if(cityRepository.existsByCityCode(request.getCityCode())) {
            throw new RuntimeException("city with given code already existed") ;
        }
        City city = CityMapper.toEntity(request);
        City result = cityRepository.save(city);
        return CityMapper.toCityResponse(result);

    }

    @Override
    @Cacheable(cacheNames = "cities", key = "#id")

    public CityResponse getCityById(Long id) {
        City city = cityRepository.findById(id).orElseThrow(
                () -> new RuntimeException("city not found with this id")
        );
        return CityMapper.toCityResponse(city);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cities", key = "#id"),
            @CacheEvict(cacheNames = "citiesByCode", allEntries = true)
    })
    public CityResponse updateCity(Long id, CityRequest request) {
        City city = cityRepository.findById(id).orElseThrow(
                () -> new RuntimeException("city not found with this id")
        ) ;
        if(cityRepository.existsByCityCode(request.getCityCode())) {
            throw new RuntimeException("city with given code already existed") ;
        }
        City updatedCity = cityRepository.save(CityMapper.updateEntity(city , request));
        return CityMapper.toCityResponse(updatedCity);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "cities", key = "#id"),
            @CacheEvict(cacheNames = "citiesByCode", allEntries = true)
    })
    public void deleteCity(Long id) {
        cityRepository.findById(id).orElseThrow(
                () -> new RuntimeException("city not found with this id")
        );
        cityRepository.deleteById(id);

    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityMapper::toCityResponse);

    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return cityRepository.searchByKeyword(keyword,pageable).map(CityMapper::toCityResponse) ;
    }

    @Override
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        return cityRepository.findByCountryCodeIgnoreCase(countryCode , pageable).map(CityMapper::toCityResponse) ;

    }

    @Override
    public boolean cityExists(String cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }


}
