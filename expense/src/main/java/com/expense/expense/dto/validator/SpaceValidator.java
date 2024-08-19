package com.expense.expense.dto.validator;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Component;

import com.expense.expense.dto.SpaceAddDto;
import com.expense.expense.dto.SpaceUpdate;

@Component
public class SpaceValidator {

    public void validateSpaceAddDto(SpaceAddDto savingSpaceAddDto) {
        if(savingSpaceAddDto.getName() == null)
            throw new RuntimeException("The name field cannot be null");
        if(savingSpaceAddDto.getDescription() == null){
            savingSpaceAddDto.setDescription("");
        }
    }

    public void validateSpaceUpdateDto(SpaceUpdate savingSpaceUpdate) {
        if(savingSpaceUpdate.getId() == null)
            throw new RuntimeException("The spaceId field cannot be null");
        if(savingSpaceUpdate.getName() == null)
            savingSpaceUpdate.setName("");
        if(savingSpaceUpdate.getDescription() == null){
            savingSpaceUpdate.setDescription("");
        }
    }
    
}
