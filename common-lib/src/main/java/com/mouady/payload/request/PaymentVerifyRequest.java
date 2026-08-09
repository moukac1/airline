package com.mouady.payload.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentVerifyRequest {

    // RazorPay specific fields
    private String razorpayPaymentId ;

    // String specific fields
    private String stripePaymentIntentId ;

}
