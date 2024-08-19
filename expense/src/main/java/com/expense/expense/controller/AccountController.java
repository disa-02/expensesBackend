package com.expense.expense.controller;

import org.springframework.web.bind.annotation.RestController;

import com.expense.expense.dto.AccountDto;
import com.expense.expense.dto.AccountSaveDto;
import com.expense.expense.dto.AccountUpdateDto;
import com.expense.expense.dto.OperationAddDto;
import com.expense.expense.dto.OperationDto;
import com.expense.expense.dto.OperationUpdateDto;
import com.expense.expense.dto.validator.AccountDtoValidator;
import com.expense.expense.service.AccountService;

import jakarta.persistence.PreRemove;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/account")
@PreAuthorize("(#userId == principal.id)")
@CrossOrigin
public class AccountController {
    
    @Autowired
    AccountService accountService;

    @Autowired
    AccountDtoValidator accountDtoValidator;

    @PostMapping("/{userId}")
    public ResponseEntity<AccountDto> addAccount(@PathVariable Integer userId,@RequestBody AccountSaveDto accountSaveDto) {
        // String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accountDtoValidator.validateAccountSaveDto(accountSaveDto);
        AccountDto accountDto = accountService.addAccount(userId, accountSaveDto);
        return new ResponseEntity<>(accountDto,HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AccountDto>> getAccounts(@PathVariable Integer userId) {
        List<AccountDto> accounts = accountService.getAccounts(userId);
        return new ResponseEntity<List<AccountDto>>(accounts,HttpStatus.OK);
    }

    @GetMapping("/{userId}/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Integer userId, @PathVariable Integer accountId) {
        AccountDto accountDto = accountService.getAccount(userId, accountId);
        return new ResponseEntity<AccountDto>(accountDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Integer userId, @PathVariable Integer accountId){
        accountService.delAccount(userId, accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Integer userId, @RequestBody AccountUpdateDto accountUpdateDto) {
        accountDtoValidator.validateAccountUpdateDto(accountUpdateDto);
        AccountDto accountDto = accountService.updateAccount(userId,accountUpdateDto);
        return new ResponseEntity<AccountDto>(accountDto, HttpStatus.OK);
    }



}
