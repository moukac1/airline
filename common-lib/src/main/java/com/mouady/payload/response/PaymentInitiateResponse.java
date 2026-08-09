package com.mouady.payload.response;


import com.mouady.enums.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInitiateResponse {

    private Long paymentId;
    private PaymentGateway gateway;
    private String transactionId;
    // razorpay fields
    private String razorpayOrderId;
    private Double amount;
    private String description;

    // frontend should redirect user to this url for payment
    private String checkoutUrl ;
    private String message ;
    private Boolean success;



}
