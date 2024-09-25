package com.tallerfinal.tallerfinal.repository;

import com.tallerfinal.tallerfinal.model.Cashout;
import com.tallerfinal.tallerfinal.model.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    Flux<Cashout> findByUserIdAndType(String userId, String type);
}
