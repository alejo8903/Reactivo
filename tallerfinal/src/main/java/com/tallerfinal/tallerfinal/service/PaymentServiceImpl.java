package com.tallerfinal.tallerfinal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private UserService userService;


    @Override
    public Mono<Boolean> validatePayment(String userId, Double amount) {
        return userService.getUserById(userId)
                .map(user -> user.getBalance() >= amount);
    }
}
