package com.expense.expense.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"username","message","jwt","status"})
public class AuthResponseDto {
    private Integer id;
    private String username;
    private String message;
    private String jwt;
    private boolean status;
}
