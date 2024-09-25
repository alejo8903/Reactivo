package com.tallerfinal.tallerfinal.service;

import com.tallerfinal.tallerfinal.model.Cashout;
import com.tallerfinal.tallerfinal.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Mono<Transaction> createTransaction(Transaction transaction);

    Flux<Cashout> getCashoutsByUserId(String userId);
}
