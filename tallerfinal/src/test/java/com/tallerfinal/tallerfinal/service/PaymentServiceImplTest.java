package com.tallerfinal.tallerfinal.service;

import com.tallerfinal.tallerfinal.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class PaymentServiceImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validatePayment_ShouldReturnTrue_WhenBalanceIsSufficient() {
        String userId = "user123";
        Double amount = 100.0;
        User user = new User();
        user.setBalance(200.0);
        when(userService.getUserById(userId)).thenReturn(Mono.just(user));
        Mono<Boolean> result = paymentService.validatePayment(userId, amount);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void validatePayment_ShouldReturnFalse_WhenBalanceIsInsufficient() {
        String userId = "user123";
        Double amount = 300.0;
        User user = new User();
        user.setBalance(200.0);
        when(userService.getUserById(userId)).thenReturn(Mono.just(user));
        Mono<Boolean> result = paymentService.validatePayment(userId, amount);

        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void validatePayment_ShouldReturnEmpty_WhenUserNotFound() {
        String userId = "user123";
        Double amount = 100.0;
        when(userService.getUserById(userId)).thenReturn(Mono.empty());
        Mono<Boolean> result = paymentService.validatePayment(userId, amount);

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }
}