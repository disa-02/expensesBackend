package com.expense.expense.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.expense.expense.dto.AccountDto;
import com.expense.expense.dto.AccountSaveDto;
import com.expense.expense.entity.Account;

@Component
public class AccountMapper {
    
    private ModelMapper modelMapper = new ModelMapper();
    
    public AccountMapper(){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    /* Account -- AccountSaveDto */
    public Account accountSaveDtoToAccount(AccountSaveDto accountSaveDto){
        return modelMapper.map(accountSaveDto, Account.class);
    }

    public AccountSaveDto accountToAccountSaveDto(Account account){
        return modelMapper.map(account, AccountSaveDto.class);
    }

    /* Account -- AccountDto */
    public Account accountDtoToAccount(AccountDto accountDto){
        return modelMapper.map(accountDto, Account.class);
    }

    public AccountDto accountToAccountDto(Account account){
        return modelMapper.map(account, AccountDto.class);
    }
}
