package com.mouady.service;


import com.mouady.model.Fare;
import com.mouady.payload.request.FareRequest;
import com.mouady.payload.response.FareResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface FareService {

    FareResponse createFare(FareRequest request) ;
    FareResponse getFareById(Long id);
    List<FareResponse> createFares(List<FareRequest> requests);
    //List<FareResponse> getFaresByFlightIdAndCabinClassId(
          //  Long id,
           // FareRequest request
    //);

    @Transactional(readOnly = true)
    List<FareResponse> getFaresByFlightIdAndCabinClassId(Long flightId, Long cabinClassId);

    FareResponse updateFare(
            Long id,
            FareRequest request
    );
    void deleteFare(Long id);
    FareResponse getFareByIdWithDetails(Long id);
    List<FareResponse> getFaresByFlightId(Long flightId);
    List<FareResponse> getFaresByFlightIdWithDetails(Long flightId);

    List<Fare> getFares();
    Map<Long, FareResponse> getLowestFarePerFlight(
            List<Long> flightIds, Long cabinClassId);
    FareResponse getLowestFareForFlightAndCabin(Long flightId, Long cabinClassId);
    Map<Long, FareResponse> getFaresByIds(List<Long> ids);

}

