package com.mouady.service.gateway;


import com.mouady.model.Payment;
import com.mouady.payload.dto.UserDTO;
import com.mouady.payload.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RazorpayService {

    @Value("${razorpay.api.key}")
    private String razorpayKeyId;
    @Value("${razorpay.api.secret}")
    private String razorpaySecret;
    @Value("${razorpay.callback.base-url}")
    private String callbackUrl;

    public PaymentLinkResponse createPaymentLink(UserDTO user, Payment payment) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpaySecret);
        BigDecimal amount =  BigDecimal.valueOf(payment.getAmount());
        Long amountInDirham = amount.multiply(BigDecimal.valueOf(10)).longValue();
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amountInDirham) ;
        paymentLinkRequest.put("currency" , "DHM");
        paymentLinkRequest.put("description" , payment.getTransactionId());
        // customer details
        JSONObject customer = new JSONObject();
        customer.put("name", user.getFullName()) ;
        customer.put("email" , user.getEmail()) ;
        if(user.getPhone() != null) {
            customer.put("contact" , user.getPhone());
        }
        paymentLinkRequest.put("customer" , customer) ;
        // notification settings
        JSONObject notify = new JSONObject();
        notify.put("email",true) ;
        notify.put("sms", user.getPhone()!= null) ;
        paymentLinkRequest.put("notify" , notify) ;

        // enable reminders

        paymentLinkRequest.put("reminder_enable", true);

        // callback config
        String successUrl = callbackUrl + "/booking-success/" + payment.getBookingId();
        paymentLinkRequest.put("callback_url", successUrl);
        paymentLinkRequest.put("callback_method", "get");

        // additional metadata
        JSONObject notes = new JSONObject();
        notes.put("user_id", user.getId()) ;
        notes.put("payment_id", payment.getId());
        notes.put("booking_id", payment.getBookingId()) ;

        paymentLinkRequest.put("notes", notes) ;



        // create payment link

        PaymentLink paymentLink = razorpay.paymentLink.create(paymentLinkRequest);
        String paymentUrl = paymentLink.get("short_url") ;
        String paymentLinkId = paymentLink.get("id") ;

        PaymentLinkResponse response = PaymentLinkResponse.builder()
                .payment_link_id(paymentLinkId)
                .payment_link_url(paymentUrl)
                .build();


        return response ;


    }

    public JSONObject fetchPaymentsDetails(String paymentId) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpaySecret);
        com.razorpay.Payment   payment =  razorpay.payments.fetch(paymentId);
        return payment.toJson() ;
    }


}
