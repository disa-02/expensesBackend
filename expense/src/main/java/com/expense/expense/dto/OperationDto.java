package com.expense.expense.dto;

import lombok.Data;

@Data
public class OperationDto {
    private Integer id;
    private String name;
    private String description;
    private Double amount;
    private String date;
    private String type;
    private String accountName;
}
