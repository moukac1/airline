package com.mouady.payload.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityRequest {
    @NotBlank(message = "city is required")
    @Size(max = 100)
    private String name;
    @NotBlank(message = "city code is required")
    @Size(max = 10)
    private String cityCode;
    @NotBlank(message = "country code is required")
    @Size(max = 5)
    private String countryCode;
    @NotBlank(message = "city is required")
    @Size(max = 100)
    private String countryName;
    @Size(max = 10)
    private String regionCode;
    @Size(max = 10)
    private String timeZoneOffset;

}
