package com.expense.expense.dto.validator;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.expense.expense.dto.OperationAddDto;
import com.expense.expense.dto.OperationUpdateDto;
import com.expense.expense.dto.TransferOperationDto;

@Component
public class OperationDtoValidator {
    
    public void validateOperationAddDto(OperationAddDto operationAddDto){
        if(operationAddDto.getAccountId() == null)
            throw new RuntimeException("The accountId field cannot be null");
        if(operationAddDto.getName() == null)
            operationAddDto.setName("");
        if(operationAddDto.getDescription() == null)
            operationAddDto.setDescription("");
        if(operationAddDto.getAmount() == null)
            throw new RuntimeException("The amount field cannot be null");
        else if(operationAddDto.getAmount() < 0)
            throw new RuntimeException("Only positive amount available");
        // if(operationAddDto.getDate() == null){
        //     String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
        //     SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        //     LocalDateTime now = LocalDateTime.now(); 
        //     operationAddDto.setDate(formatter.format(now));
        // }
        if(operationAddDto.getType() == null){
            throw new RuntimeException("The type field cannot be null");
        }
    }

    public void validateOperationUpdateDto(OperationUpdateDto operationDto) {
        if(operationDto.getId() == null)
            throw new RuntimeException("The accountId field cannot be null");
        if(operationDto.getName() == null)
            operationDto.setName("");
        if(operationDto.getDescription() == null)
            operationDto.setDescription("");
        // if(operationDto.getDate() == null){
        //     operationDto.setDate("");
        // }
    }

    public void validateTransferDto(TransferOperationDto transferDto) {
        if(transferDto.getSourceAccountId() == null)
            throw new RuntimeException("The sourceAccountid field cannot be null"); 
        if(transferDto.getDestinationAccountId() == null)
            throw new RuntimeException("The destinationAccountid field cannot be null"); 
        if(transferDto.getAmount() == null)
            throw new RuntimeException("The amount field cannot be null"); 
        if(transferDto.getAmount() <= 0)
            throw new RuntimeException("The amount field must be positive"); 
    }
}
