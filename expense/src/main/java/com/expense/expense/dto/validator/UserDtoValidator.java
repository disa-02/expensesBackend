package com.expense.expense.dto.validator;

import org.springframework.stereotype.Component;

import com.expense.expense.dto.UserRegisterDto;
import com.expense.expense.dto.UserUpdateDto;

@Component
public class UserDtoValidator {
    
    public void validateUserRegisterDto(UserRegisterDto userRegisterDto){
        if(userRegisterDto.getUsername() == null)
            throw new RuntimeException("The username field cannot be null");
        if(userRegisterDto.getUsername() == null)
            throw new RuntimeException("The password field cannot be null");
    }

    public void validateUserUpdateDto(UserUpdateDto userUpdate) {
        if(userUpdate.getId() == null)
            throw new RuntimeException("The username field cannot be null");
        if(userUpdate.getUsername() == null)
            userUpdate.setUsername("");
        if(userUpdate.getPassword() == null)
            userUpdate.setPassword("");
    }
}
