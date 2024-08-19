package com.expense.expense.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense.expense.dto.UserDto;
import com.expense.expense.dto.UserRegisterDto;
import com.expense.expense.dto.UserUpdateDto;
import com.expense.expense.dto.validator.UserDtoValidator;
import com.expense.expense.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    UserService userService;

    @Autowired
    UserDtoValidator userDtoValidator;

    // @PostMapping()
    // public ResponseEntity<UserRegisterDto> createUser(@RequestBody UserRegisterDto userDto) {
    //     userDtoValidator.validateUserRegisterDto(userDto);
    //     UserRegisterDto user = userService.addUser(userDto);
    //     return new ResponseEntity<UserRegisterDto>(user, HttpStatus.CREATED);
    // }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("(#id == principal.id)")
    public ResponseEntity<UserDto> getById(@PathVariable Integer id) {
        UserDto user = userService.getUserById(id);
        return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("(#userUpdate.id == principal.id)")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserUpdateDto userUpdate) {
        userDtoValidator.validateUserUpdateDto(userUpdate);
        UserDto userDto = userService.updateUser(userUpdate);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("(#id == principal.id)")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    
}
