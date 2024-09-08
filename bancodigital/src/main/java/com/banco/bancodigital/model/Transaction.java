package com.banco.bancodigital.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@NoArgsConstructor
@Data
@Document(collection = "transactions")
public class Transaction {
    @MongoId
    private String transactionId;
    private String accountId;
    private Double amount;

    public Transaction(String transactionId, String accountId, Double amount) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
    }


}
