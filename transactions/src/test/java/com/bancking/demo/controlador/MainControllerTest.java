package com.bancking.demo.controlador;

import com.bancking.demo.domain.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MainControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void processTransaction() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100.0);
        transaction1.setFee(0.2);
        Transaction transaction2 = new Transaction();
        transaction2.setAmount(100.0);
        transaction2.setFee(0.2);
        List<Transaction> transactions = List.of(transaction1, transaction2);
        webTestClient.post().uri("/processTransactions")
                .bodyValue(transactions)
                .exchange()
                .expectStatus().isOk();
    }
}