package com.expense.expense.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense.expense.dto.OperationAddDto;
import com.expense.expense.dto.OperationDto;
import com.expense.expense.dto.OperationUpdateDto;
import com.expense.expense.dto.validator.OperationDtoValidator;
import com.expense.expense.service.AccountOperationService;
import com.expense.expense.service.SavingSpaceOperationService;
import com.expense.expense.service.WorkSpaceOperationService;

@RestController
@RequestMapping("/savingSpace/operation")
@PreAuthorize("(#userId == principal.id)")
@CrossOrigin
public class SavingSpaceOperationController {
    @Autowired
    SavingSpaceOperationService workSpaceOperationService;;
    
    @Autowired
    OperationDtoValidator operationDtoValidator;
    
    @PostMapping("/{userId}/{spaceId}")
    public ResponseEntity<OperationDto> addOperation(@PathVariable Integer userId, @PathVariable Integer spaceId, @RequestBody OperationAddDto operationDto) {
        operationDtoValidator.validateOperationAddDto(operationDto);
        OperationDto newOperationDto = workSpaceOperationService.addOperation(userId,spaceId,operationDto);
        return new ResponseEntity<>(newOperationDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/{spaceId}")
    public ResponseEntity<?> updateOperation(@PathVariable Integer userId, @PathVariable Integer spaceId, @RequestBody OperationUpdateDto operationDto) throws ParseException {
        operationDtoValidator.validateOperationUpdateDto(operationDto);
        workSpaceOperationService.updateOperation(userId,spaceId,operationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/{spaceId}")
    public ResponseEntity<List<OperationDto>> getOperations(@PathVariable Integer userId, @PathVariable Integer spaceId) {
        List<OperationDto> operations = workSpaceOperationService.getOperations(userId,spaceId);
        return new ResponseEntity<>(operations,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{spaceId}/{operationId}")
    public ResponseEntity<?> deleteOperation(@PathVariable Integer userId, @PathVariable Integer spaceId,@PathVariable Integer operationId){
        workSpaceOperationService.deleteOperation(userId, spaceId, operationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
