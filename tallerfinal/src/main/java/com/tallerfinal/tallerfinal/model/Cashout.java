package com.tallerfinal.tallerfinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "cashouts")
public class Cashout {
    @Id
    private String id;
    private String userId;
    private Double amount;


}