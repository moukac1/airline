package com.mouady.payload.request;

import com.mouady.embeddable.ContactInfo;
import com.mouady.enums.CabinClassType;
import com.mouady.enums.TripType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotNull(message = "Flight Instance ID is required")
    private Long flightInstanceId;

    @NotNull(message = "Cabin class is required")
    private CabinClassType cabinClass;

//    @NotNull(message = "Trip type is required")
    private TripType tripType;

    @NotNull(message = "Fare ID is required")
    private Long fareId;

    @NotNull(message = "At least one passenger is required")
    @Size(min = 1, message = "At least one passenger is required")
    private List<PassengerRequest> passengers;

    private ContactInfo contactInfo;

    private List<Long> ancillaryIds;
    private List<Long> mealIds;

    private String promoCode;

    private List<String> seatNumbers;
}
