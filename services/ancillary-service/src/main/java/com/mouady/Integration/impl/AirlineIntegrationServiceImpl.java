package com.mouady.Integration.impl;

;
import com.mouady.Integration.AirlineIntegrationService;
import com.mouady.client.AirlineClient;
import com.mouady.payload.response.AircraftResponse;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirlineIntegrationServiceImpl implements AirlineIntegrationService {

    private final AirlineClient airlineClient;

    @Override
    public Long getAirlineIdForUser(Long userId) {
        try {
            return airlineClient.getAirlineByOwner(userId).getId();
        } catch (FeignException.NotFound e) {
            throw new EntityNotFoundException("No airline found for user: " + userId);
        } catch (FeignException e) {
            throw new RuntimeException("Airline service error: " + e.getMessage(), e);
        }
    }

    @Override
    public AircraftResponse getAircraftById(Long aircraftId) {
        try {
            return airlineClient.getAircraftById(aircraftId);
        } catch (FeignException.NotFound e) {
            throw new EntityNotFoundException("No aircraft found for id: " + aircraftId);
        } catch (FeignException e) {
            throw new RuntimeException("Aircraft service error: " + e.getMessage(), e);
        }
    }
}
