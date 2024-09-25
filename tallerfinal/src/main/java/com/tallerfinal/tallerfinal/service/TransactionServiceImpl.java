package com.tallerfinal.tallerfinal.service;

import com.tallerfinal.tallerfinal.model.Cashout;
import com.tallerfinal.tallerfinal.model.Transaction;
import com.tallerfinal.tallerfinal.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Mono<Transaction> createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Flux<Cashout> getCashoutsByUserId(String userId) {
        return transactionRepository.findByUserIdAndType(userId, "cashout");
    }
}
