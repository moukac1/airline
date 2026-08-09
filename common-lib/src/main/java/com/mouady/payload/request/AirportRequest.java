package com.mouady.payload.request;

import com.mouady.embeddable.Address;
import com.mouady.embeddable.GeoCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportRequest {

    @NotBlank(message = "iata code is required")
    @Size(min=3 , max=3 , message = "3 characters are required")
    private String iataCode;
    @Valid
    private Address address;
    @NotNull(message = "city id is mandatory")
    private Long cityId;

    private String detailedName ;

    @Valid
    private GeoCode geoCode;
    private String name ;
    private String timeZone;
}
