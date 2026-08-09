package com.mouady.payload.response;


import com.mouady.embeddable.Support;
import com.mouady.enums.AirlineStatus;
import com.mouady.payload.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineResponse {
    private Long id;

    private String iataCode;
    private String icaoCode;


    private String name;
    private String alias;

    private String logoUrl;
    private String website;


    private String country;

    private AirlineStatus status;
    private String alliance;

    private Instant CreatedAt ;
    private Instant UpdatedAt ;


    private UserDTO owner;
    private Long ownerId;
    private Long updatedById;


    private CityResponse headquartersCity ;
    private Support support;


}
