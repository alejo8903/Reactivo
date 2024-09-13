package com.bancking.demo.service;

import com.bancking.demo.domain.Transaction;
import com.bancking.demo.domain.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction validTransaction;

    @BeforeEach
    public void setUp() {
        validTransaction = new Transaction();
        validTransaction.setAmount(1000);
        validTransaction.setFee(0.02);
        validTransaction.setId("123");

        Mockito.when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        Mockito.when(webClientBuilder.build()).thenReturn(webClient);
        Mockito.when(webClient.post()).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.uri("/calculateFees")).thenReturn(requestBodySpec);
        Mockito.doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(any());
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.bodyToMono(Transaction.class)).thenReturn(Mono.just(validTransaction));

        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(Mono.just(validTransaction));
    }

    @Test
    void testPrepareTransactions_Success() {
        Flux<Transaction> transactionsFlux = Flux.just(validTransaction);

        Mono<Transaction> resultMono = transactionService.prepareTransactions(transactionsFlux);

        StepVerifier.create(resultMono)
                .expectNextMatches(transaction -> transaction.getAmount() == 1000 && transaction.getFee() == 0.02)
                .verifyComplete();

        Mockito.verify(transactionRepository, Mockito.times(1)).save(any(Transaction.class));
    }

}
