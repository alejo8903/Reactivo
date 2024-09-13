package com.bancking.demo.service;

import com.bancking.demo.domain.Transaction;
import com.bancking.demo.domain.TransactionRepository;
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

import java.util.List;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TransactionConfirmationServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @InjectMocks
    private TransactionConfirmationService transactionConfirmationService;


    @Test
    void confirmTransactions(){

        Mockito.when(webClientBuilder.baseUrl("http://localhost:8080")).thenReturn(webClientBuilder);
        Mockito.when(webClientBuilder.build()).thenReturn(webClient);
        Mockito.when(webClient.post()).thenReturn( requestBodySpec);
        Mockito.when(requestBodySpec.uri("/executeBatch")).thenReturn(requestBodySpec);
        Mockito.doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(any());
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        Transaction t1 = new Transaction();
        t1.setAmount(1000);
        t1.setFee(0.02);
        t1.setId("xxxxxxxx-xxxxxx-xxxxxx-xxxxxx");
        Transaction t2 = new Transaction();
        t2.setAmount(2000);
        t2.setFee(0.03);
        t2.setId("xxxxxxxx-xxxxxx-xxxxxx-xxxxxx");
        Transaction t3 = new Transaction();
        t3.setAmount(3000);
        t3.setFee(0.04);
        t3.setId("xxxxxxxx-xxxxxx-xxxxxx-xxxxxx");
        List<Transaction> transactions = List.of(t1, t2, t3);
        Flux<Transaction> transactionFlux = Flux.fromIterable(transactions);

        Mono<Void> result = transactionConfirmationService.confirmTransactions(transactionFlux);
        StepVerifier.create(result).verifyComplete();

    }
}
