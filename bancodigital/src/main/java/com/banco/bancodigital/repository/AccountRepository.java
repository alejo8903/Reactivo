package com.banco.bancodigital.repository;

import com.banco.bancodigital.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
    Mono<Object> findByAccountId(String accountId);
}
