package com.tallerfinal.tallerfinal.service;

import com.tallerfinal.tallerfinal.model.Cashout;
import com.tallerfinal.tallerfinal.model.Transaction;
import com.tallerfinal.tallerfinal.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void createTransaction() {
        Transaction transaction = new Transaction();
        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));
        Mono<Transaction> result = transactionService.createTransaction(transaction);
        assertNotNull(result);
        assertEquals(transaction, result.block());
    }

    @Test
    void getCashoutsByUserId() {
        String userId = "user123";
        Cashout cashout = new Cashout("1", userId, 100.0);
        when(transactionRepository.findByUserIdAndType(userId, "cashout"))
                .thenReturn(Flux.just(cashout));
        Flux<Cashout> result = transactionService.getCashoutsByUserId(userId);
        StepVerifier.create(result)
                .expectNext(cashout)
                .verifyComplete();
    }
}
