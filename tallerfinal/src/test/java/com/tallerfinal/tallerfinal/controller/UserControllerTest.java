package com.tallerfinal.tallerfinal.controller;


import com.tallerfinal.tallerfinal.model.User;
import com.tallerfinal.tallerfinal.service.CashoutService;
import com.tallerfinal.tallerfinal.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    UserService userService;

    @MockBean
    CashoutService cashoutService;

    static {
        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("MONGO_URI", dotenv.get("MONGO_URI"));
    }

    @Test
    void createUser() {
        User user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setBalance(1000.0);
        when(userService.createUser(user)).thenReturn(Mono.just(user));
        webTestClient.post()
                .uri("/users")
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.name").isEqualTo("John Doe")
                .jsonPath("$.balance").isEqualTo(1000.0);
    }



    @Test
    void getUserById() {
        User user = new User();
        user.setId("1");
        user.setName("Jane Doe");
        user.setBalance(2000.0);
        when(userService.getUserById("1")).thenReturn(Mono.just(user));
        when(userService.createUser(user)).thenReturn(Mono.just(user));
        User createdUser = webTestClient.post()
                .uri("/users")
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody();

        webTestClient.get()
                .uri("/users/{id}", createdUser.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(createdUser.getId())
                .jsonPath("$.name").isEqualTo("Jane Doe")
                .jsonPath("$.balance").isEqualTo(2000.0);


    }

    @Test
    void getUserById_NotFound() {
        when(userService.getUserById("999")).thenReturn(Mono.empty());
        webTestClient.get()
                .uri("/users/{id}", 999)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }
}