package com.tallerfinal.tallerfinal.service;

import com.tallerfinal.tallerfinal.model.Cashout;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CashoutService {
    public Mono<Cashout> createCashout(String userId, Double amount);

    public Flux<Cashout> getCashoutsByUserId(String userId);

    Flux<Cashout> findByUserId(String userId);
}
