package com.expense.expense.dto.validator;

import org.springframework.stereotype.Component;

import com.expense.expense.dto.AccountSaveDto;
import com.expense.expense.dto.AccountUpdateDto;

@Component
public class AccountDtoValidator {
    

    public void validateAccountSaveDto(AccountSaveDto accountSaveDto){
        if(accountSaveDto.getName() == null) 
            throw new RuntimeException("The name field cannot be null");
        if(accountSaveDto.getDescription() == null)
            accountSaveDto.setDescription("");
        if(accountSaveDto.getBalance() == null)
            accountSaveDto.setBalance(0.0);
    }

    public void validateAccountUpdateDto(AccountUpdateDto accountUpdateDto){
        if(accountUpdateDto.getId() == null)
            throw new RuntimeException("The id field cannot be null");
        if(accountUpdateDto.getName() == null)
            accountUpdateDto.setName("");
        if(accountUpdateDto.getDescription() == null)
            accountUpdateDto.setDescription("");

    }
}
