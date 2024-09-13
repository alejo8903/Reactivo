package com.bancking.demo.service;

import com.bancking.demo.domain.Transaction;
import com.bancking.demo.domain.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TransactionConfirmationService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<Void> confirmTransactions(Flux<Transaction> transactions){
        return transactions
                .buffer(10)
                .flatMap(this::executeTransactions)
                .flatMap(transactionRepository::saveAll)
                .then();
    }

    private Mono<List<Transaction>> executeTransactions(List<Transaction> transactions) {
        WebClient client = webClientBuilder.baseUrl("http://localhost:8080").build();
        return client.post()
                .uri("/executeBatch")
                .bodyValue(transactions)
                .retrieve()
                .bodyToMono(Void.class)
                .then(Mono.just(transactions));
    }
}
