package com.mouady.service.impl;


import com.mouady.client.UserClient;
import com.mouady.enums.PaymentGateway;
import com.mouady.enums.PaymentStatus;
import com.mouady.enums.UserRole;
import com.mouady.event.PaymentEventProducer;
import com.mouady.mapper.PaymentMapper;
import com.mouady.model.Payment;
import com.mouady.payload.dto.PaymentDTO;
import com.mouady.payload.dto.UserDTO;
import com.mouady.payload.request.PaymentInitiateRequest;
import com.mouady.payload.request.PaymentVerifyRequest;
import com.mouady.payload.response.PaymentInitiateResponse;
import com.mouady.payload.response.PaymentLinkResponse;
import com.mouady.repository.PaymentRepository;
import com.mouady.service.PaymentService;
import com.mouady.service.gateway.RazorpayService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;
    private final PaymentEventProducer paymentEventProducer;
    private final UserClient userClient;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws RazorpayException {

        paymentRepository.findByBookingId(request.getBookingId())
                .ifPresent(
                        payment -> {
                            /*
                             here the condition must be if status is success by booking id ,
                            but we don't have problem if it was failed
                             */
                    if(payment.getStatus()== PaymentStatus.SUCCESS){
                        throw new RuntimeException("payment already done with this booking id "+ payment.getBookingId());
                    }
                });
        Payment payment = Payment.builder()
                .userId(request.getUserId())
                .bookingId(request.getBookingId())
                .amount(request.getAmount())
                .status(PaymentStatus.PENDING)
                .provider(request.getGateway())
                .transactionId(generateTransactionId())
                .build();
        payment = paymentRepository.save(payment);

        PaymentInitiateResponse response = PaymentInitiateResponse.builder()
                .paymentId(payment.getId())
                .gateway(request.getGateway())
                .transactionId(payment.getTransactionId())
                .amount(request.getAmount())
                .description(request.getDescription())
                .success(true)
                .message("successful initiation")
                .build();

        if(request.getGateway()== PaymentGateway.RAZORPAY){
            // to do fetch user details when we lll communicate our microservices by feign Client
            UserDTO user=userClient.getUserById(payment.getUserId());

            //  create razorpay payment link using rayzorpay service
            PaymentLinkResponse paymentLinkResponse = razorpayService.createPaymentLink(
                    user, payment
            ) ;
            // set payment link to payment initiate response

            response.setRazorpayOrderId(paymentLinkResponse.getPayment_link_id());
            response.setCheckoutUrl(paymentLinkResponse.getPayment_link_id());

        }
        return response;
    }

    private String generateTransactionId() {
        return "TXN_" + System.currentTimeMillis() + "_" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();

    }


    @Override
    public PaymentDTO verifyPayment(PaymentVerifyRequest request) throws RazorpayException {
        JSONObject paymentDetails = razorpayService.fetchPaymentsDetails(
                request.getRazorpayPaymentId()
        );
        String status = paymentDetails.optString("status");
        JSONObject notes = paymentDetails.optJSONObject("notes");
        Long paymentId = Long.parseLong(notes.optString("payment_id")) ;
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                () -> new RuntimeException("payment not found")
        );
        boolean isValid = "captured".equalsIgnoreCase(status);
        if(isValid){
            if(payment.getProvider()== PaymentGateway.RAZORPAY){
                payment.setProviderPaymentId(request.getRazorpayPaymentId());
            }
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());
            paymentRepository.save(payment);
            System.out.println("send payment event and payment status is : "+status);
            paymentEventProducer.sendPaymentCompleted(payment);

        }else{
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason("Payment verification failed");
            paymentRepository.save(payment);
            //kafka here
            paymentEventProducer.sendPaymentFailed(payment);

        }
        return PaymentMapper.toDTO(payment);
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(PaymentMapper::toDTO) ;
        // we stopped it here
    }

    @Override
    public Map<Long, PaymentDTO> getAllPaymentsByBookingIds(List<Long> bookingIds) {
        return paymentRepository.findByBookingIdIn(bookingIds)
                .stream().collect(Collectors.toMap(
                        Payment::getId, PaymentMapper::toDTO)
                );
    }
}
