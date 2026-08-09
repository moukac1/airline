package com.mouady.mapper;

import com.mouady.model.Payment;
import com.mouady.payload.dto.PaymentDTO;

import java.time.ZoneId;

public class PaymentMapper {

    public static PaymentDTO toDTO(Payment payment) {
        if (payment == null) {return null;}
        PaymentDTO dto  = new PaymentDTO() ;
                dto.setId(payment.getId()) ;
                dto.setGateway(payment.getProvider());
                dto.setAmount(payment.getAmount());
                dto.setTransactionId(payment.getTransactionId());
                dto.setGatewayPaymentId(payment.getProviderPaymentId());
                dto.setStatus(payment.getStatus());
                dto.setUserId(payment.getUserId());
                dto.setBookingId(payment.getBookingId());

        if(payment.getPaidAt() != null) {
            dto.setCompletedAt(payment.getPaidAt())  ;
        }
        if(payment.getCreatedAt() != null) {
            dto.setCreateAt(
                    payment.getCreatedAt()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
            );

            dto.setInitiatedAt(
                    payment.getCreatedAt()
                            .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            );
        }
        if(payment.getUpdatedAt() != null) {
            dto.setUpdateAt(
                    payment.getUpdatedAt()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
            );
        }
        return dto ;
    }
    //public static Payment toPayment(PaymentDTO dto) {
        //return
    //}
}
