package com.expense.expense.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.expense.expense.dto.AccountBalanceDto;
import com.expense.expense.entity.AccountBalance;

@Component
public class AccountBalanceMapper {
    
    private ModelMapper modelMapper = new ModelMapper();
    
    public AccountBalanceMapper(){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /* AccountBalance - AccountBalanceDto */
    public AccountBalance accountBalanceDtoToAccountBalance(AccountBalanceDto accountBalanceDto){
        return modelMapper.map(accountBalanceDto, AccountBalance.class);
    }

    public AccountBalanceDto accountBalanceToAccountBalanceDto(AccountBalance accountBalance){
        AccountBalanceDto accountBalanceDto = modelMapper.map(accountBalance, AccountBalanceDto.class);
        accountBalanceDto.setName(accountBalance.getAccount().getName());
        return accountBalanceDto;
    }
}
