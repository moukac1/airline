package com.mouady.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mouady.embeddable.Address;
import com.mouady.embeddable.GeoCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true, length = 3)
    private String iataCode;
    @Column(nullable = false)
    private String name;
    @Embedded
    private Address address;
    @Embedded
    private GeoCode geoCode ;
    @Column(name = "time_zone_id" , length=50)
    private String timeZoneId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "city_id")
    private City city;

    @JsonIgnore
    @Transient
    public String getDetailedName(){
        if(city != null && city.getCountryCode() != null){
            return name.toUpperCase() + "/" + city.getCityCode().toUpperCase();
        }
        return name.toUpperCase();
    }
}
