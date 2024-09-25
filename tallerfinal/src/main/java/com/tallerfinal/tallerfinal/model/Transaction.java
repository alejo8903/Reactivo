package com.tallerfinal.tallerfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "transactions")
public class Transaction {

    private String userId;
    private Double amount;
    private String type;

}
