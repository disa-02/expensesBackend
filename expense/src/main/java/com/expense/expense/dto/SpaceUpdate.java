package com.expense.expense.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class SpaceUpdate {
    private Integer id;
    private String name;
    private String description;
}
