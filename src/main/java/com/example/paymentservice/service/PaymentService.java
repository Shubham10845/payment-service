package com.example.paymentservice.service;

import com.example.paymentservice.dto.PaymentRequestDto;
import com.example.paymentservice.dto.PaymentResponseDto;
import com.example.paymentservice.paymentgateway.PaymentGateway;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {
    PaymentGateway paymentGateway;
    public PaymentResponseDto initiatePayment(PaymentRequestDto paymentRequestDto) throws StripeException {
        return paymentGateway.createPayment(paymentRequestDto.paymentMethodId(), paymentRequestDto.amount());
    }
}
