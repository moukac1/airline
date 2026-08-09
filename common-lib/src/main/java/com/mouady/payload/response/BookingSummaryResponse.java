package com.mouady.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSummaryResponse {
    private Long id;
    private String bookingReference;
    private String status;
    private Double totalAmount;
    private Instant bookingDate;
}
