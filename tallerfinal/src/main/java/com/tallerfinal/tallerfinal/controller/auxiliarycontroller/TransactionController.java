package com.tallerfinal.tallerfinal.controller.auxiliarycontroller;

import com.tallerfinal.tallerfinal.model.Cashout;
import com.tallerfinal.tallerfinal.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("cashout/user/{userId}")
    public Flux<Cashout> getTransactionsByUserId(@PathVariable String userId) {
        return transactionService.getCashoutsByUserId(userId);
    }
}
