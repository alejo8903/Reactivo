package com.tallerfinal.tallerfinal.service;

import com.tallerfinal.tallerfinal.dto.CashoutDto;
import com.tallerfinal.tallerfinal.model.Cashout;
import com.tallerfinal.tallerfinal.model.Transaction;
import com.tallerfinal.tallerfinal.repository.CashoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CashoutServiceImpl implements CashoutService {

    private final CashoutRepository cashoutRepository;
    private final UserService userService;
    private final TransactionService transactionService;
    private final WebClient webClient;

    @Override
    public Mono<Cashout> createCashout(String userId, Double amount) {
        return validateCashout(userId, amount)
                .flatMap(validAmount -> createTransaction(userId, validAmount))
                .flatMap(transaction -> {
                    Cashout cashout = new Cashout(null, userId, transaction.getAmount());
                    return cashoutRepository.save(cashout);
                });
    }

    @Override
    public Flux<Cashout> getCashoutsByUserId(String userId) {
        return webClient.get()
                .uri("/transactions/cashout/user/" + userId)
                .retrieve()
                .bodyToFlux(Cashout.class)
                .onErrorResume(error -> {
                    System.err.println("Error al obtener el historial de transacciones: " + error.getMessage());
                    return Flux.empty();
                });
    }

    @Override
    public Flux<Cashout> findByUserId(String userId) {
        return cashoutRepository.findByUserId(userId);
    }

    public Mono<Transaction> createTransaction(String userId, Double amount) {
        return transactionService.createTransaction(new Transaction(userId, (amount * -1), "cashout"));
    }

    public Mono<Double> validateCashout(String userId, Double amount) {
        CashoutDto cashoutDto = new CashoutDto(userId, amount);
        return webClient.post()
                .uri("/payments/validate")
                .bodyValue(cashoutDto)
                .retrieve()
                .bodyToMono(Boolean.class)
                .flatMap(valid -> {
                    if (valid) {
                        return Mono.just(amount);
                    } else {
                        return Mono.empty();
                    }
                });
    }
}
