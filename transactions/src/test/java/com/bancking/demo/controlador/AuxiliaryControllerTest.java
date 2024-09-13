package com.bancking.demo.controlador;

import com.bancking.demo.domain.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuxiliaryControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void calculateFees() {
        Transaction transaction = new Transaction();
        transaction.setAmount(100.0);
        transaction.setFee(0.2);
        webTestClient.post().uri("/calculateFees")
                .bodyValue(transaction)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.fee").isEqualTo(2.0);
    }

    @Test
    void executeBatch() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100.0);
        transaction1.setFee(0.2);
        Transaction transaction2 = new Transaction();
        transaction2.setAmount(100.0);
        transaction2.setFee(0.2);
        List<Transaction> transactions = List.of(transaction1, transaction2);
        webTestClient.post().uri("/executeBatch")
                .bodyValue(transactions)
                .exchange()
                .expectStatus().isOk();
    }
}