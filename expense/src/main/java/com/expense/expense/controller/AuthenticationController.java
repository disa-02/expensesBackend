package com.expense.expense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense.expense.dto.AuthLoginRequestDto;
import com.expense.expense.dto.AuthResponseDto;
import com.expense.expense.dto.UserRegisterDto;
import com.expense.expense.service.UserDetailServiceImp;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {
    
    @Autowired
    UserDetailServiceImp userDetailsService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthLoginRequestDto userRequest){
        return new ResponseEntity<>(userDetailsService.loginUser(userRequest),HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> signup(@RequestBody UserRegisterDto userRequest) {     
        return new ResponseEntity<>(userDetailsService.createUser(userRequest),HttpStatus.OK);
    }
    
}
