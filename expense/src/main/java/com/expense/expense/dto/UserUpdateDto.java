package com.expense.expense.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private Integer id;
    private String username;
    private String password;
}
