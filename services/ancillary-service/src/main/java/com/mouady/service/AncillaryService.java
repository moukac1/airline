package com.mouady.service;


import com.mouady.exception.ResourceNotFoundException;
import com.mouady.payload.request.AncillaryRequest;
import com.mouady.payload.response.AncillaryResponse;

import java.util.List;

public interface AncillaryService {

    AncillaryResponse create(Long userId, AncillaryRequest request) throws ResourceNotFoundException;

    AncillaryResponse getById(Long id) throws ResourceNotFoundException;

    List<AncillaryResponse> getAllByAirlineId(Long userId);

    AncillaryResponse update(Long id, AncillaryRequest request) throws ResourceNotFoundException;

    void delete(Long id);
}
