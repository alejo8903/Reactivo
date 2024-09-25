package com.tallerfinal.tallerfinal.controller;

import com.tallerfinal.tallerfinal.model.Cashout;
import com.tallerfinal.tallerfinal.service.CashoutService;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CashoutControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private CashoutService cashoutService;

    static {
        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("MONGO_URI", dotenv.get("MONGO_URI"));
    }

    @Test
    void createCashout() {
        Map<String, Object> request = new HashMap<>();
        request.put("userId", "user123");
        request.put("amount", 150.0);
        when(cashoutService.createCashout("user123", 150.0)).thenReturn(Mono.just(new Cashout("cashout","user123", 150.0)));
        webTestClient.post()
                .uri("/cashouts")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.userId").isEqualTo("user123")
                .jsonPath("$.amount").isEqualTo(150.0);
    }

    @Test
    void getCashoutsByUserId() {
        String userId = "user123";
        Map<String, Object> request = new HashMap<>();
        request.put("userId", userId);
        request.put("amount", 200.0);
        when(cashoutService.createCashout(userId, 200.0)).thenReturn(Mono.just(new Cashout(userId, "user", 200.0)));
        webTestClient.post()
                .uri("/cashouts")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();

        when(cashoutService.getCashoutsByUserId(userId)).thenReturn(Flux.just(new Cashout("cashout123",userId, 200.0)));
        webTestClient.get()
                .uri("/cashouts/user/{userId}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cashout.class)
                .hasSize(1);
    }

    @Test
    void getCashoutsByNonExistentUserId() {
        webTestClient.get()
                .uri("/cashouts/user/{userId}", "nonexistentUser")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cashout.class)
                .hasSize(0);
    }
}