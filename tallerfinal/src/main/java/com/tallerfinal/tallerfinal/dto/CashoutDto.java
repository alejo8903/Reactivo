package com.tallerfinal.tallerfinal.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CashoutDto {
    private String userId;
    private Double amount;


}
