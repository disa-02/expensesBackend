package com.expense.expense.dto;

import lombok.Data;

@Data
public class TransferOperationDto {
    private Integer sourceAccountId;
    private Double amount;
    private Integer destinationAccountId;
}
