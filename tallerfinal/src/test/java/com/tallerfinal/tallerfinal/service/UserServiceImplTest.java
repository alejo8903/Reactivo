package com.tallerfinal.tallerfinal.service;

import com.tallerfinal.tallerfinal.model.Transaction;
import com.tallerfinal.tallerfinal.model.User;
import com.tallerfinal.tallerfinal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserById() {
        User user = new User();
        user.setId("user123");
        user.setName("User");
        user.setBalance(100.0);

        when(userRepository.findById(anyString())).thenReturn(Mono.just(user));
        Mono<User> result = userService.getUserById("user123");

        assertNotNull(result);
        assertEquals(user, result.block());
    }

    @Test
    void createUser() {
        User user = new User();
        user.setId("user123");
        user.setName("User");
        user.setBalance(100.0);

        when(userRepository.save(user)).thenReturn(Mono.just(user));
        Mono<User> result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(user, result.block());

    }

    @Test
    void updateUserBalance() {
        User user = new User();
        user.setId("user123");
        user.setName("User");
        user.setBalance(100.0);

        when(userRepository.findById(anyString())).thenReturn(Mono.just(user));
        when(transactionService.createTransaction(any())).thenReturn(Mono.just(new Transaction(user.getId(), 100.0, "Add funds")));
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        Mono<User> result = userService.updateUserBalance("user123", 100.0);

        assertNotNull(result);
        user.setBalance(user.getBalance() + 100.0);
        assertEquals(user, result.block());
    }
}