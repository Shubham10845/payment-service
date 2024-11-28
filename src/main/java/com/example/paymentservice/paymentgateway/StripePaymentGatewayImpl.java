package com.example.paymentservice.paymentgateway;

import com.example.paymentservice.dto.PaymentResponseDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StripePaymentGatewayImpl implements PaymentGateway{

    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentResponseDto createPayment(String paymentMethodId, Long amount) throws StripeException {
        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount(amount) // Amount in smallest currency unit (e.g., cents for USD)
                .setCurrency("usd")
                .setPaymentMethod(paymentMethodId)
                .setConfirm(true)
                .setAutomaticPaymentMethods(PaymentIntentCreateParams
                        .AutomaticPaymentMethods
                        .builder()
                        .setEnabled(true)
                        .setAllowRedirects(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER
                        )
                        .build())
                .build();
        PaymentIntent paymentIntent = PaymentIntent.create(createParams);
        return Optional.ofNullable(paymentIntent)
                .map(PaymentIntent::getStatus)
                .filter("succeeded"::equals)
                .map(res -> new PaymentResponseDto(paymentIntent.getPaymentMethod(),"Payment Successful "))
                .orElseThrow(() -> new RuntimeException("Payment failed with status: " + paymentIntent.getStatus()));
    }
}
