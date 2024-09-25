package com.tallerfinal.tallerfinal.controller;

import com.tallerfinal.tallerfinal.model.Cashout;
import com.tallerfinal.tallerfinal.service.CashoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/cashouts")
public class CashoutController {

    @Autowired
    private CashoutService cashoutService;

    @PostMapping
    public Mono<Cashout> createCashout(@RequestBody Map<String, Object> request) {
        String userId = (String) request.get("userId");
        Double amount = ((Number) request.get("amount")).doubleValue();
        return cashoutService.createCashout(userId, amount);
    }

    @GetMapping("/user/{userId}")
    public Flux<Cashout> getCashoutsByUserId(@PathVariable String userId) {
        return cashoutService.getCashoutsByUserId(userId);
    }
}