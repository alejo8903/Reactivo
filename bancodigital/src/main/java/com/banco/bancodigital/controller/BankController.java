package com.banco.bancodigital.controller;

import com.banco.bancodigital.model.*;
import com.banco.bancodigital.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    @Autowired
    private BankService bankService;
    //ok
    @GetMapping("/accounts/{accountId}/balance")
    public Mono<Double> getBalance(@PathVariable String accountId) {
        return bankService.getBalance(accountId);
    }
    //ok
    @PostMapping("/accounts/transfer")
    public Mono<String> transferMoney(@RequestBody TransferRequest request) {
        return bankService.transferMoney(request);
    }
    //ok
    @GetMapping("/accounts/{accountId}/transactions")
    public Flux<Transaction> getTransactions(@PathVariable String accountId) {
        return bankService.getTransactions(accountId);
    }
    //ok
    @PostMapping("/accounts/create")
    public Mono<String> createAccount(@RequestBody CreateAccountRequest request) {
        return bankService.createAccount(request);
    }
    //ok
    @DeleteMapping("/accounts/{accountId}")
    public Mono<String> closeAccount(@PathVariable String accountId) {
        return bankService.closeAccount(accountId);
    }
    //ok
    @PutMapping("/accounts/update")
    public Mono<String> updateAccount(@RequestBody UpdateAccountRequest request) {
        return bankService.updateAccount(request);
    }
    //ok
    @GetMapping("/accounts/{accountId}/profile")
    public Mono<CustomerProfile> getCustomerProfile(@PathVariable String accountId) {
        return bankService.getCustomerProfile(accountId);
    }
    //ok
    @GetMapping("/customers/{customerId}/loans")
    public Flux<Loan> getActiveLoans(@PathVariable String customerId) {
        return bankService.getActiveLoans(customerId);
    }
    //ok
    @GetMapping("/accounts/{accountId}/interest")
    public Flux<Double> simulateInterest(@PathVariable String accountId) {
        return bankService.simulateInterest(accountId);
    }
    //ok
    @GetMapping("/loans/{loanId}")
    public Mono<String> getLoanStatus(@PathVariable String loanId) {
        return bankService.getLoanStatus(loanId);
    }

}
