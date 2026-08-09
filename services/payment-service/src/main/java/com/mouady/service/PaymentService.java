package com.mouady.service;


import com.mouady.payload.dto.PaymentDTO;
import com.mouady.payload.request.PaymentInitiateRequest;
import com.mouady.payload.request.PaymentVerifyRequest;
import com.mouady.payload.response.PaymentInitiateResponse;
import com.razorpay.RazorpayException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws RazorpayException;
    PaymentDTO verifyPayment(PaymentVerifyRequest request) throws RazorpayException;
    Page<PaymentDTO> getAllPayments(Pageable pageable) ;
    Map<Long , PaymentDTO> getAllPaymentsByBookingIds(List<Long> bookingIds);



}
