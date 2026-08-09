package com.mouady.client;

import com.mouady.enums.CabinClassType;
import com.mouady.payload.response.CabinClassResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class SeatClientFallback implements SeatClient {

    @Override
    public List<CabinClassResponse> getCabinClassesByAircraftId(Long aircraftId) {
        return Collections.emptyList();
    }

    @Override
    public CabinClassResponse getCabinClassByAircraftIdAndName(CabinClassType cabinClass, Long id) {
        return null;
    }
}
