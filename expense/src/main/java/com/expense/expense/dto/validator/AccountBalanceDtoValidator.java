package com.expense.expense.dto.validator;

import com.expense.expense.dto.AccountBalanceDto;
import com.expense.expense.dto.AccountBalanceUpdateDto;

public class AccountBalanceDtoValidator {
    
    public void validateAccountBalanceUpdateDto(AccountBalanceUpdateDto accountBalanceUpdateDto){
        if(accountBalanceUpdateDto.getId() == null)
            throw new RuntimeException("The accountBalance field cannot be null");
        if(accountBalanceUpdateDto.getWorkSpaceId() == null)
            throw new RuntimeException("The spaceOperationId field cannot be null");
        if(accountBalanceUpdateDto.getLimitMoney() == null)
            throw new RuntimeException("The limitMoney field cannot be null");
    }
}
