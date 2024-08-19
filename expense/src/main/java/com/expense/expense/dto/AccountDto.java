package com.expense.expense.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AccountDto {
    private Integer id;
    private String name;
    private String description;
    private Double balance;
    private Double availableMoney;
}
