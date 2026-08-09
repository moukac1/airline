package com.mouady.embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BoardingBenefits {
    @Column(name = "priority_checkin", nullable = false)
    @Builder.Default
    private Boolean priorityCheckIn = false ;
    @Column(name = "priority_boarding", nullable = false)
    @Builder.Default
    private Boolean priorityBoarding = false ;
    @Column( name = "fast_track_security", nullable = false)
    @Builder.Default
    private Boolean fastTrackSecurity=false;

}
