package com.bancking.digital.controlador;

import com.bancking.digital.domain.Transaction;
import com.bancking.digital.service.TransactionConfirmationService;
import com.bancking.digital.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MainController {
    private final TransactionService transactionService;
    private final TransactionConfirmationService transactionConfirmationService;

    public MainController(TransactionService transactionService, TransactionConfirmationService transactionConfirmationService){

        this.transactionService = transactionService;
        this.transactionConfirmationService = transactionConfirmationService;
    }

    @PostMapping("/processTransactions")
    public Mono<Void> processTransaction(@RequestBody Flux<Transaction> transactions){
        Mono<Transaction> preparedTransactions = transactionService.prepareTransactions(transactions);
        return preparedTransactions
                .zipWhen(preparedTransaction -> transactionConfirmationService.confirmTransactions(Flux.just(preparedTransaction)))
                .then();

    }


}
