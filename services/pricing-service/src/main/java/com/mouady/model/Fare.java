package com.mouady.model;


import com.mouady.embeddable.*;
import com.mouady.enums.CabinClassType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name ;
    @Column(nullable = false)
    private Character rbCode;
    @Column(nullable = false)
    private Long flightId;
    @Column(nullable = false)
    private Long cabinClassId ;
    @Enumerated(EnumType.STRING)
    private CabinClassType cabinClass ;
    @Column(nullable = true)
    private Double baseFare ;
    private Double airlineFees ;
    private Double taxesAndFees ;
    @Column(nullable = false)
    private Double currentPrice ;

    private String fareLabel ;

    @ManyToOne
    @JoinColumn(name = "baggage_policy_id")
    private BaggagePolicy baggagePolicy;
    @ManyToOne
    @JoinColumn(name = "fare_rule_id")
    private FareRule fareRule;
    @Embedded
    @Builder.Default
    private SeatBenefits seatBenefits = new SeatBenefits();
    @Embedded
    @Builder.Default
    private BoardingBenefits boardingBenefits = new BoardingBenefits() ;
    @Embedded
    @Builder.Default
    private InFlightBenefits inFlightBenefits = new InFlightBenefits() ;

    @Embedded
    @Builder.Default
    private FlexibilityBenefits flexibilityBenefits = new FlexibilityBenefits() ;
    @Embedded
    @Builder.Default
    private PremiumServiceBenefits premiumServiceBenefits = new PremiumServiceBenefits();


    @CreationTimestamp
    private Instant createdAt ;
    @UpdateTimestamp
    private Instant updatedAt ;

    public Double getTotalPrice() {
        return baseFare
                + (airlineFees != null ? airlineFees : 0.0)
                + (taxesAndFees != null ? taxesAndFees : 0.0);
    }

}
