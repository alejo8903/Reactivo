package com.banco.bancodigital.service;

import com.banco.bancodigital.model.*;
import com.banco.bancodigital.repository.AccountRepository;
import com.banco.bancodigital.repository.CustomerProfileRepository;
import com.banco.bancodigital.repository.LoanRepository;
import com.banco.bancodigital.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BankService {

    @Autowired
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final LoanRepository loanRepository;

    private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

    public Mono<Double> getBalance(String accountId) {
        // Caso de uso: Consultar el saldo actual de una cuenta bancaria. Sino hay balance se debe tener un valor de 0.0

        return accountRepository.findById(accountId)
                .map(Account::getBalance)
                .switchIfEmpty(Mono.just(0.0));
        // Implementar la lógica de consulta aquí
    }

    public Mono<String> transferMoney(TransferRequest request) {
        // Caso de uso: Transferir dinero de una cuenta a otra. Hacer llamado de otro flujo simulando el llamado
        Transaction from = new Transaction();
        Transaction to = new Transaction();
        from.setAccountId(request.getFromAccount());
        from.setAmount(-request.getAmount());
        to.setAccountId(request.getToAccount());
        to.setAmount(request.getAmount());
        return accountRepository.findById(request.getFromAccount())
                .flatMap(fromAccount -> {
                    if (fromAccount.getBalance() < request.getAmount()) {
                        return Mono.error(new Exception("Insufficient funds"));  // Error si no hay fondos suficientes
                    }
                    fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());

                    return accountRepository.findById(request.getToAccount())
                            .flatMap(toAccount -> {
                                toAccount.setBalance(toAccount.getBalance() + request.getAmount());
                                return accountRepository.save(fromAccount)
                                        .then(accountRepository.save(toAccount))
                                        .then(transactionRepository.save(from))
                                        .then(transactionRepository.save(to))
                                        .then(Mono.just("Transfer successful from account " + request.getFromAccount() + " to " + request.getToAccount()));
                            });
                })
                .onErrorResume(e -> Mono.just("Error: " + e.getMessage()));
    }
        // Implementar la lógica de consulta aquí  ???????


    public Flux<Transaction> getTransactions(String accountId) {
        // Caso de uso: Consultar el historial de transacciones de una cuenta bancaria.

        return transactionRepository.findAllByAccountId(accountId); // Implementar la lógica de consulta aquí
    }

    public Mono<String> createAccount(CreateAccountRequest request) {
        // Caso de uso: Crear una nueva cuenta bancaria con un saldo inicial.
        Account account = new Account();
        account.setAccountId(request.getAccountId());
        account.setBalance(request.getInitialBalance());
        Transaction transaction = new Transaction();
        transaction.setAccountId(request.getAccountId());
        transaction.setAmount(request.getInitialBalance());
        return accountRepository.findByAccountId(request.getAccountId())
                .flatMap(existingAcount ->
                        Mono.just( "Account whit ID " + request.getAccountId() + " alredy exists."))
                .switchIfEmpty(
                        accountRepository.save(account)
                                .flatMap(
                                        accountRequest -> transactionRepository.save(transaction)
                                                .then(Mono.just("Accoun created successfully whit ID: " + accountRequest.getAccountId())))
                                .onErrorResume(e -> Mono.just("Error creating account: " + e.getMessage()))
                );
        // Implementar la lógica de consulta aquí
    }

    public Mono<String> closeAccount(String accountId) {
        // Caso de uso: Cerrar una cuenta bancaria especificada. Verificar que la ceunta exista y si no existe debe retornar un error controlado
        return accountRepository.findById(accountId)
                .flatMap(account ->
                    accountRepository.delete(account).thenReturn(
                            "Account with ID " + accountId + " has been closed.")
                ).switchIfEmpty(Mono.just("Account not found with ID " + accountId));
        // Implementar la lógica de consulta aquí
    }

    public Mono<String> updateAccount(UpdateAccountRequest request) {
        // Caso de uso: Actualizar la información de una cuenta bancaria especificada. Verificar que la ceunta exista y si no existe debe retornar un error controlado

        return accountRepository.findById(request.getAccountId())
                .flatMap(account -> {
                    account.setBalance(Double.parseDouble(request.getNewData().trim()));
                    return accountRepository.save(account)
                            .then(Mono.just("Account updated successfully"));
                })
                .switchIfEmpty(Mono.just("Account not found"))
                .onErrorResume(e -> Mono.just("Error updating account: " + e.getMessage()));

        // Implementar la lógica de consulta aquí
    }

    public Mono<CustomerProfile> getCustomerProfile(String accountId) {
        // Caso de uso: Consultar el perfil del cliente que posee la cuenta bancaria. Obtener los valores por cada uno de los flujos y si no existe alguno debe presentar un error

        return accountRepository.findById(accountId)
                .flatMap(
                        account -> customerProfileRepository.findByCustomerId(account.getCustomerId())
                                .switchIfEmpty(Mono.error(new Exception("Customer profile not found")))
                );
        // Implementar la lógica de consulta aquí
    }

    public Flux<Loan> getActiveLoans(String customerId) {
        // Caso de uso: Consultar todos los préstamos activos asociados al cliente especificado.

        return loanRepository.findAllActiveLoansByCustomerId(customerId);
        // Implementar la lógica de consulta aquí
    }

    public Flux<Double> simulateInterest(String accountId) {
        double principal = 1000.00;
        double rate = 0.05;

        // Caso de uso: Simular el interés compuesto en una cuenta bancaria. Sacar un rago de 10 años y aplicar la siguiente formula = principal * Math.pow(1 + rate, year)
        return accountRepository.findById(accountId).map(
                account -> {
                    List<Double> interest = new ArrayList<>();
                    for (int i = 1; i <= 10; i++) {
                        interest.add( principal * Math.pow(1 + rate, i));
                    }
                    return Flux.fromIterable(interest);
                }
        ).flatMapMany(Flux::from);
        // Implementar la lógica de consulta aquí
    }

    public Mono<String> getLoanStatus(String loanId) {
        // Caso de uso: Consultar el estado de un préstamo. se debe tener un flujo balanceMono y interestRateMono. Imprimir con el formato siguiente el resultado   "Loan ID: %s, Balance: %.2f, Interest Rate: %.2f%%"
        return loanRepository.findById(loanId)
                .map(loan ->
                        {
                            if (loan.getActive()){
                                return String.format("Loan ID: %s, Balance: %.2f, Interest Rate: %.2f%%", loan.getLoanId(), loan.getBalance(), loan.getInterestRate());
                            } else {
                                return "Loan not found";
                            }
                        }

                );
        // Implementar la lógica de consulta aquí
    }


}
