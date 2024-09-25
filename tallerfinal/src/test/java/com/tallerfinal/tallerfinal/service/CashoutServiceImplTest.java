package com.tallerfinal.tallerfinal.service;

import com.tallerfinal.tallerfinal.dto.CashoutDto;
import com.tallerfinal.tallerfinal.model.Cashout;
import com.tallerfinal.tallerfinal.model.Transaction;
import com.tallerfinal.tallerfinal.repository.CashoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CashoutServiceImplTest {

    @Mock
    private CashoutRepository cashoutRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private CashoutServiceImpl cashoutService;


    @Test
    void testFindByUserId() {
        String userId = "user123";
        Cashout cashout = new Cashout("1", userId, 100.0);

        when(cashoutRepository.findByUserId(userId)).thenReturn(Flux.just(cashout));

        Flux<Cashout> result = cashoutService.findByUserId(userId);

        StepVerifier.create(result)
                .expectNext(cashout)
                .verifyComplete();
    }

    @Test
    void testCreateTransaction() {
        String userId = "user123";
        Double amount = 100.0;
        Transaction transaction = new Transaction(userId, -100.0, "cashout");

        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(Mono.just(transaction));

        Mono<Transaction> result = cashoutService.createTransaction(userId, amount);

        StepVerifier.create(result)
                .expectNextMatches(t -> t.getUserId().equals(userId) && t.getAmount().equals(-100.0))
                .verifyComplete();
    }


}
