package com.mouady.payload.request;


import com.mouady.embeddable.Support;
import com.mouady.enums.AirlineStatus;
import com.mouady.payload.dto.UserDTO;
import com.mouady.payload.response.CityResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirlineRequest {

    @NotBlank(message = "it's mandatory ")
    @Size(max=2 , min=2 , message = "IATA code must be 2 characters only")
    private String iataCode;
    @NotBlank(message = "it's mandatory ")
    @Size(max=3 , min=3 , message = "IATA code must be 3 characters only")
    private String icaoCode;

    @NotBlank(message = "it's mandatory ")
    private String name;
    private String alias;

    private String logoUrl;
    private String website;

    private Long headquartersCityId ;
    @NotBlank
    private String country;



    private AirlineStatus status;
    private String alliance;

    private String supportEmail ;
    private String supportPhone ;
    private String supportHours ;



}
