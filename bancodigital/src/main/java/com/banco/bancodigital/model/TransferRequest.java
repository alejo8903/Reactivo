package com.banco.bancodigital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransferRequest {
    private String fromAccount;
    private String toAccount;
    private Double amount;

}
