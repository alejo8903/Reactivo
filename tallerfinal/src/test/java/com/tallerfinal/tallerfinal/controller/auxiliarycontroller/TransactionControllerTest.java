package com.tallerfinal.tallerfinal.controller.auxiliarycontroller;


import com.tallerfinal.tallerfinal.model.Cashout;
import com.tallerfinal.tallerfinal.model.Transaction;
import com.tallerfinal.tallerfinal.repository.TransactionRepository;
import com.tallerfinal.tallerfinal.service.TransactionService;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TransactionControllerTest {

    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private WebTestClient webTestClient;

    static {
        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("MONGO_URI", dotenv.get("MONGO_URI"));
    }

    @Test
    void getTransactions() {
        Cashout t1 = new Cashout();
        t1.setUserId("1");
        t1.setAmount(100.0);

        when(transactionRepository.findByUserIdAndType("1", "cashout")).thenReturn(Flux.just(t1));
        webTestClient.get()
                .uri("/transactions/cashout/user/" + t1.getUserId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].userId").isEqualTo("1")
                .jsonPath("$[0].amount").isEqualTo(100.0);
    }



}