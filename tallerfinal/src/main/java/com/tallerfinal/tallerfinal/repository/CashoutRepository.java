package com.tallerfinal.tallerfinal.repository;

import com.tallerfinal.tallerfinal.model.Cashout;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


public interface CashoutRepository extends ReactiveMongoRepository<Cashout, String> {
    Flux<Cashout> findByUserId(String userId);


}
