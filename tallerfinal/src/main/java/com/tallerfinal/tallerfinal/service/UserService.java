package com.tallerfinal.tallerfinal.service;

import com.tallerfinal.tallerfinal.model.User;
import reactor.core.publisher.Mono;

public interface UserService {
    public Mono<User> updateUserBalance(String id, Double amount);
    public Mono<User> getUserById(String id);
    public Mono<User> createUser(User user);
}
