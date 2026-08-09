package com.mouady.service;

import com.mouady.payload.request.CityRequest;
import com.mouady.payload.response.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface CityService  {
     CityResponse createCity(CityRequest cityRequest);
     CityResponse getCityById(Long id);
     CityResponse updateCity(Long id , CityRequest request);
     void deleteCity(Long id);

     Page<CityResponse> getAllCities(Pageable pageable);
     Page<CityResponse> searchCities(String keyword, Pageable pageable);
     Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable);
     boolean cityExists(String cityCode);
     // boolean validateCityCode(String cityCode);
}
