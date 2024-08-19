package com.expense.expense.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.expense.expense.entity.Account;
import com.expense.expense.entity.RoleEntity;
import com.expense.expense.entity.WorkSpace;

import lombok.Data;

@Data
public class UserRegisterDto {

    private String username;
    private String password;

}
