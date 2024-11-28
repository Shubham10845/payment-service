package com.example.paymentservice.dto;

public record PaymentRequestDto(String paymentMethodId, long amount) {
}
