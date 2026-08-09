package com.mouady.embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InFlightBenefits {
    @Column(name = "complimentary_meals", nullable = false)
    @Builder.Default
    private Boolean complimentaryMeals = false;

    @Column(nullable = false, name = "premium_meals")
    @Builder.Default
    private Boolean premiumMealChoice = false;
    @Column(nullable = false, name = "in_flight_internet")
    @Builder.Default
    private Boolean inFlightInternet = false;
    @Column(nullable = false, name = "in_flight_entertainment")
    private Boolean inFlightEntertainment ;










}
