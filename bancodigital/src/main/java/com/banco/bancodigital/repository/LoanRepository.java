package com.banco.bancodigital.repository;

import com.banco.bancodigital.model.Loan;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface LoanRepository extends ReactiveMongoRepository<Loan, String> {
    @Query("{ 'customerId' : ?0, 'active' : true }")
    Flux<Loan> findAllActiveLoansByCustomerId(String customerId);
}
