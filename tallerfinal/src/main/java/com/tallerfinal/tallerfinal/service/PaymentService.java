package com.tallerfinal.tallerfinal.service;

import reactor.core.publisher.Mono;

public interface PaymentService {
    public Mono<Boolean> validatePayment(String userId, Double amount);
}
