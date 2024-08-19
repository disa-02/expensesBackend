package com.expense.expense.dto;

import lombok.Data;

@Data
public class OperationAddDto {
    private Integer accountId;
    private String name;
    private String description;
    private Double amount;
    private String date;
    private OperationType type;
}
