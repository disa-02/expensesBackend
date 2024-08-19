package com.expense.expense.dto;

import lombok.Data;

@Data
public class AccountBalanceDto {
    private Integer id;
    private String name;
    private Double limitMoney;
    private Double balance;
}
