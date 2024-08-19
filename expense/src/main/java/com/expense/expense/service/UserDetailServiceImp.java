package com.expense.expense.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.expense.expense.Security.CustomPrincipal;
import com.expense.expense.dto.AuthLoginRequestDto;
import com.expense.expense.dto.AuthResponseDto;
import com.expense.expense.dto.UserRegisterDto;
import com.expense.expense.entity.UserEntity;
import com.expense.expense.mapper.UserMapper;
import com.expense.expense.repository.UserRepository;
import com.expense.expense.utils.JwtUtils;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    UserMapper userMapper;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

        return userEntity;
    }

    public AuthResponseDto loginUser(AuthLoginRequestDto authLoginRequestDto){
        String username = authLoginRequestDto.getUsername();
        String password = authLoginRequestDto.getPassword();
        Integer[] id = new Integer[1];
        Authentication authenticate = this.authenticate(username,password,id);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String accesToken = jwtUtils.generateToken(authenticate);
        AuthResponseDto authResponseDto = new AuthResponseDto(id[0],username,"User loged successfuly", accesToken, true);
        return authResponseDto;
    }
    
    public Authentication authenticate(String username, String password,Integer[] id){
        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
        id[0] = userEntity.getId();
        if(!passwordEncoder.matches(password, userEntity.getPassword()))
            throw new BadCredentialsException("Invalid password");

        return new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword(),userEntity.getAuthorities());
    }

    public AuthResponseDto createUser(UserRegisterDto userRegisterDto){
        UserEntity user = userMapper.userRegisterDtoToUser(userRegisterDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNoExpired(true);
        user.setAccountNoLocked(true);
        user.setCredentialNoExpired(true);
        user.setEnabled(true);
        UserEntity newUser = userRepository.save(user);
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

        String accessToken = jwtUtils.generateToken(authentication);
        AuthResponseDto authResponseDto = new AuthResponseDto(newUser.getId(),user.getUsername(),"User created successfuly", accessToken, true);
        return authResponseDto;
    }
}
