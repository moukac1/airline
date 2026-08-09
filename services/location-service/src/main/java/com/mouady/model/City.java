package com.mouady.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false )
    private String cityCode;
    @Column(nullable = false )
    private String countryCode;
    @Column(nullable = false )
    private String countryName;
    @Size(min = 0, max = 10)
    private String regionCode;
    @Column(name= "time_zone_id" , length = 50 )
    private String timeZoneId;


}
