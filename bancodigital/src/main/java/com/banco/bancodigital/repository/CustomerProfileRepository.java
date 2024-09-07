package com.banco.bancodigital.repository;

import com.banco.bancodigital.model.CustomerProfile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerProfileRepository extends ReactiveMongoRepository<CustomerProfile, String> {
    Mono<CustomerProfile> findByCustomerId(String customerId);
}
