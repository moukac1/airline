package com.mouady.payload.request;

import com.mouady.enums.PaymentGateway;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInitiateRequest {

    @NotNull(message = "user id is mandatory")
    private Long userId;

    @NotNull(message = "booking id is required")
    private Long bookingId;
    @NotNull(message = "gateway payment id is required")
    private PaymentGateway gateway;


    @Positive( message = "amount must be positive")
    @NotNull(message = "amount must be filled")
    private Double amount;

    private String description;






}
