package com.expense.expense.dto;


import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
public class SpaceAddDto {
    private String name;
    private String description;
}
