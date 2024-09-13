package com.bancking.demo.controlador.cont;

import com.bancking.demo.controlador.MainController;
import com.bancking.demo.domain.Transaction;
import com.bancking.demo.service.TransactionConfirmationService;
import com.bancking.demo.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.*;

@WebFluxTest(controllers = MainController.class)
public class MainControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    TransactionService transactionService;

    @MockBean
    TransactionConfirmationService transactionConfirmationService;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
        transaction.setAmount(1000);
        transaction.setFee(0.02);
        transaction.setId("xxxxxxxx-xxxxxx-xxxxxx-xxxxxx");

        Mockito.when(transactionService.prepareTransactions(any(Flux.class)))
                .thenReturn(just(transaction));

        Mockito.when(transactionConfirmationService.confirmTransactions(any(Flux.class)))
                .thenReturn(empty());

    }

    @Test
    public void processTransactionTest() {
        webTestClient.post()
                .uri("/processTransactions")
                .bodyValue(transaction)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(transactionService, Mockito.times(1)).prepareTransactions(any(Flux.class));
        Mockito.verify(transactionConfirmationService, Mockito.times(1)).confirmTransactions(any(Flux.class));
    }
}
