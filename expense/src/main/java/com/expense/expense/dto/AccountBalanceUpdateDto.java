package com.expense.expense.dto;

import lombok.Data;

@Data
public class AccountBalanceUpdateDto {
    
    Integer id;
    Integer workSpaceId;
    Double limitMoney;
}
