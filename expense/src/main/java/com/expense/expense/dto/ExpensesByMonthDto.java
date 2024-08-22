package com.expense.expense.dto;

import lombok.Data;

@Data
public class ExpensesByMonthDto {
    String date;
    Double expense;
}
