package com.expense.expense.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.expense.expense.dto.UserDto;
import com.expense.expense.dto.UserRegisterDto;
import com.expense.expense.dto.UserUpdateDto;
import com.expense.expense.entity.UserEntity;

@Component
public class UserMapper {
    
    private ModelMapper modelMapper = new ModelMapper();

    public UserMapper(){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /* User --- UserRegisterDto */
    public UserEntity userRegisterDtoToUser(UserRegisterDto useRegisterDto){
        return modelMapper.map(useRegisterDto, UserEntity.class);
    };

    public UserRegisterDto userToUserRegisterDto(UserEntity user){
        return modelMapper.map(user, UserRegisterDto.class);
    };

    /* User --- UserDto */
    public UserEntity userDtoToUser(UserDto userDto){
        return modelMapper.map(userDto, UserEntity.class);
    };

    public UserDto userToUserDto(UserEntity user){
        return modelMapper.map(user, UserDto.class);
    };

    /* User --- UserUpdaterDto */
    public UserEntity userUpdateDtoToUser(UserUpdateDto userUpdateDto){
        return modelMapper.map(userUpdateDto, UserEntity.class);
    };

    public UserUpdateDto userToUserUpdateDto(UserEntity user){
        return modelMapper.map(user, UserUpdateDto.class);
    };
}
