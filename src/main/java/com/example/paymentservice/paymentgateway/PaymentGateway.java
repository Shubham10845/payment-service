package com.example.paymentservice.paymentgateway;

import com.example.paymentservice.dto.PaymentResponseDto;
import com.stripe.exception.StripeException;

public interface PaymentGateway {
    PaymentResponseDto createPayment(String paymentMethodId, Long amount) throws StripeException;
}
