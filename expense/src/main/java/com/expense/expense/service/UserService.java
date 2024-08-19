package com.expense.expense.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.expense.expense.dto.UserDto;
import com.expense.expense.dto.UserRegisterDto;
import com.expense.expense.dto.UserUpdateDto;
import com.expense.expense.entity.UserEntity;
import com.expense.expense.mapper.UserMapper;
import com.expense.expense.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    
    @Transactional
    public UserRegisterDto addUser(UserRegisterDto userDto){
        UserEntity user = userMapper.userRegisterDtoToUser(userDto);
        Boolean availableUsername = userRepository.existsByUsername(user.getUsername());
        if(availableUsername){
            throw new RuntimeException("Not valid username");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.userToUserRegisterDto(user);
    }
    
    @Transactional
    public void deleteUser(Integer userId){
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(userId);
    }

    public UserDto getUserById(Integer userId){
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.userToUserDto(user);
    }

    public List<UserDto> getUsers(){
        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.userToUserDto(user))
                .collect(Collectors.toList())
                ;
    }
    
    @Transactional
    public UserDto updateUser(UserUpdateDto userUpdate){
        UserEntity user = userRepository.findById(userUpdate.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        
        if(userUpdate.getUsername() != ""){
            Boolean availableUsername = userRepository.existsByUsername(userUpdate.getUsername());
            if(availableUsername && !user.getUsername().equals(userUpdate.getUsername())){
                throw new RuntimeException("Not valid username");
            }
            user.setUsername(userUpdate.getUsername());
        }
        if(userUpdate.getPassword() != "")
            user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }
}
