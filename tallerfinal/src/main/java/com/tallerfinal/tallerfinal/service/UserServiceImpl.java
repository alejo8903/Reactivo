package com.tallerfinal.tallerfinal.service;

import com.tallerfinal.tallerfinal.model.Transaction;
import com.tallerfinal.tallerfinal.model.User;
import com.tallerfinal.tallerfinal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public Mono<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<User> updateUserBalance(String id, Double amount) {
        return userRepository.findById(id)
                .flatMap(user -> {
                    user.setBalance(user.getBalance() + amount);
                    return transactionService.createTransaction(new Transaction(user.getId(), amount, "Add funds"))
                            .flatMap(transaction -> userRepository.save(user));
                });
    }
}
