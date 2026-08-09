package com.mouady.controller;


import com.mouady.payload.dto.PaymentDTO;
import com.mouady.payload.request.PaymentInitiateRequest;
import com.mouady.payload.request.PaymentVerifyRequest;
import com.mouady.payload.response.PaymentInitiateResponse;
import com.mouady.service.PaymentService;
import com.razorpay.RazorpayException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<PaymentInitiateResponse> initiatePayment(
            @RequestBody @Valid PaymentInitiateRequest paymentInitiateRequest
    ) throws RazorpayException {
        PaymentInitiateResponse response = paymentService.initiatePayment(
                paymentInitiateRequest
        );
        return ResponseEntity.ok(response);

    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @RequestBody @Valid PaymentVerifyRequest request
    ) throws RazorpayException {
        log.info("receive payment verify request:");
        return ResponseEntity.ok(paymentService.verifyPayment(request)) ;
    }
    @PostMapping("/batch/bookings")
    public ResponseEntity<Map<Long, PaymentDTO>> getPaymentsByBookingIds(
            @RequestBody List<Long> bookingIds
    ){
        return ResponseEntity.ok(
                paymentService.getAllPaymentsByBookingIds(bookingIds)
        );
    }
    @GetMapping
    public ResponseEntity<Page<PaymentDTO>> getPayments(
            @RequestParam(defaultValue = "0") int page ,
            @RequestParam(defaultValue = "20") int size ,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection

    ){
        Sort.Direction direction =sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<PaymentDTO> payments = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(payments) ;
    }



}
