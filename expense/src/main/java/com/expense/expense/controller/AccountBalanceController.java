package com.expense.expense.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense.expense.dto.AccountBalanceDto;
import com.expense.expense.dto.AccountBalanceUpdateDto;
import com.expense.expense.entity.AccountBalance;
import com.expense.expense.service.AccountBalanceService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/accountBalance")
@PreAuthorize("(#userId == principal.id)")
@CrossOrigin
public class AccountBalanceController {
    
    @Autowired
    AccountBalanceService accountBalanceService;

    @PutMapping("/{userId}")
    public ResponseEntity<AccountBalanceDto> setLimitMoney(@PathVariable Integer userId, @RequestBody AccountBalanceUpdateDto accountBalanceUpdateDto) {
        AccountBalanceDto accountBalanceDto = accountBalanceService.setLimitMoney(userId,accountBalanceUpdateDto);
        return new ResponseEntity<>(accountBalanceDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<AccountBalanceDto> getAccountBalance(@RequestParam Integer userId, @RequestParam Integer workSpaceId,@RequestParam Integer accountBalanceId) {
        AccountBalanceDto accountBalanceDto = accountBalanceService.getAccountsBalance(userId,workSpaceId,accountBalanceId);
        return new ResponseEntity<>(accountBalanceDto,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccountBalanceDto>> listAccountsBalance(@RequestParam Integer userId, @RequestParam Integer workSpaceId) {
        List<AccountBalanceDto> accountsBalance = accountBalanceService.listAccountsBalance(userId,workSpaceId);
        return new ResponseEntity<>(accountsBalance,HttpStatus.OK);
    }
    
}
