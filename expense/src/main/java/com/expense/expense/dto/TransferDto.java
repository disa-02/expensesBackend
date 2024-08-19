package com.expense.expense.dto;

import lombok.Data;

@Data
public class TransferDto {
    private AccountDto account;
    private OperationDto operation;
}
