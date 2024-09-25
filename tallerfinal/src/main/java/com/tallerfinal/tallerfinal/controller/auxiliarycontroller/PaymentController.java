package com.tallerfinal.tallerfinal.controller.auxiliarycontroller;


import com.tallerfinal.tallerfinal.dto.CashoutDto;
import com.tallerfinal.tallerfinal.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping ("/validate")
    public Mono<Boolean> validatePayment(@RequestBody CashoutDto cashoutDto){
        return paymentService.validatePayment(cashoutDto.getUserId(), cashoutDto.getAmount());
    }

}
