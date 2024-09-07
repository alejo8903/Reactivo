package com.banco.bancodigital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "loans")
public class Loan {
    @MongoId
    private String loanId;
    private String customerId;
    private Double balance;
    private Double interestRate;
    private Boolean active;
}
