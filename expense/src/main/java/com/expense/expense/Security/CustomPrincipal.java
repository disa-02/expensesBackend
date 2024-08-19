package com.expense.expense.Security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomPrincipal {
    private Integer id;
    private String username;
}
