package com.expense.expense.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense.expense.dto.ExpensesByMonthDto;
import com.expense.expense.dto.OperationAddDto;
import com.expense.expense.dto.OperationDto;
import com.expense.expense.dto.OperationUpdateDto;
import com.expense.expense.dto.TransferOperationDto;
import com.expense.expense.dto.validator.OperationDtoValidator;
import com.expense.expense.service.AccountOperationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/account/operation")
@PreAuthorize("(#userId == principal.id)")
@CrossOrigin
public class AccountAndOperationController {
    
    @Autowired
    AccountOperationService accountOperationService;
    
    @Autowired
    OperationDtoValidator operationDtoValidator;
    
    @PostMapping("/moneyTransaction/{userId}")
    public ResponseEntity<OperationDto> moneyTransaction(@PathVariable Integer userId,@RequestBody OperationAddDto operationDto) {
        operationDtoValidator.validateOperationAddDto(operationDto);
        OperationDto newOperationDto = accountOperationService.moneyTransaction(userId,operationDto);
        return new ResponseEntity<>(newOperationDto, HttpStatus.OK);
    }

    @PutMapping("/updateOperation/{userId}/{accountId}")
    public ResponseEntity<?> updateOperation(@PathVariable Integer userId,@PathVariable Integer accountId, @RequestBody OperationUpdateDto operationDto) throws ParseException {
        operationDtoValidator.validateOperationUpdateDto(operationDto);
        accountOperationService.updateOperation(userId,accountId,operationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/{accountId}")
    public ResponseEntity<List<OperationDto>> getOperations(@PathVariable Integer userId,@PathVariable Integer accountId) {
        List<OperationDto> operations = accountOperationService.getOperations(userId,accountId);
        return new ResponseEntity<>(operations,HttpStatus.OK);
    }

    @DeleteMapping("/deleteOperation/{userId}/{accountId}/{operationId}")
    public ResponseEntity<?> deleteOperation(@PathVariable Integer userId, @PathVariable Integer accountId,@PathVariable Integer operationId){
        accountOperationService.deleteOperation(userId, accountId, operationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/transfer/{userId}")
    public ResponseEntity<List<OperationDto>> transferMoney(@PathVariable Integer userId, @RequestBody TransferOperationDto transferOperationDto) {
        operationDtoValidator.validateTransferDto(transferOperationDto);
        List<OperationDto> operations = accountOperationService.transferMoney(userId, transferOperationDto);
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }

    @GetMapping("/findExpenseByMonth/{userId}")
    public ResponseEntity<List<ExpensesByMonthDto>> findExpenseByMonth(@PathVariable Integer userId, @RequestParam Integer year) {
        List<ExpensesByMonthDto> operations = accountOperationService.findExpenseByMonth(year,userId);
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }
    
}   
