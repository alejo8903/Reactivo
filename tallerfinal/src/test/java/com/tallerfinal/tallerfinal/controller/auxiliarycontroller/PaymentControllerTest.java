package com.tallerfinal.tallerfinal.controller.auxiliarycontroller;


import com.jayway.jsonpath.JsonPath;
import com.tallerfinal.tallerfinal.model.User;
import com.tallerfinal.tallerfinal.service.PaymentService;
import com.tallerfinal.tallerfinal.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PaymentControllerTest {

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private UserService userService;

    @Autowired
    private WebTestClient webTestClient;

    static {
        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("MONGO_URI", dotenv.get("MONGO_URI"));
    }

    @Test
    void validatePayment() {
        User user = new User();
        user.setId("1");
        user.setName("Jane Doe");
        user.setBalance(2000.0);
        when(userService.getUserById("1")).thenReturn(Mono.just(user));
        when(paymentService.validatePayment("1", 1000.0)).thenReturn(Mono.just(true));
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/payments/validate")
                        .queryParam("amount", 1000.0)
                        .queryParam("userId", "1")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isEqualTo(true);
    }
}
