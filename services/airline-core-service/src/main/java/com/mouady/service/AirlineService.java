package com.mouady.service;

import com.mouady.enums.AirlineStatus;
import com.mouady.payload.request.AirlineRequest;
import com.mouady.payload.response.AirlineDropdownItem;
import com.mouady.payload.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {
    AirlineResponse createAirline(AirlineRequest request , Long ownerId);
    AirlineResponse getAirlineByOwner( Long ownerId);
    AirlineResponse updateAirline(AirlineRequest request , Long ownerId);
    void deleteAirline( Long id, Long ownerId);
    AirlineResponse getAirlineById(Long id);
    Page<AirlineResponse> getAllAirlines(Pageable pageable);
    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status);
    List<AirlineDropdownItem> getAirlinesForDropdown();

}
