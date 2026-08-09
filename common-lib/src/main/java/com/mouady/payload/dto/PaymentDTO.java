package com.mouady.payload.dto;


import com.mouady.enums.PaymentGateway;
import com.mouady.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    // user info
    private Long id;
    private Long userId;
    private String userEmail;

    private Long bookingId;
    private PaymentStatus status;
    private PaymentGateway gateway;
    private Double amount;
    private String transactionId;
    private String gatewayOrderId;
    private String gatewayPaymentId;
    private String gatewaySignature;
    private String description;
    private String failureReason;

    private LocalDateTime initiatedAt;
    private LocalDateTime completedAt;
    private Boolean notificationSent;
    private Boolean active ;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;





}
