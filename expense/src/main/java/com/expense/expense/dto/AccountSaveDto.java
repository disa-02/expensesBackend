package com.expense.expense.dto;

import lombok.Data;

@Data
public class AccountSaveDto {
    private String name;
    private String description;
    private Double balance;
}
