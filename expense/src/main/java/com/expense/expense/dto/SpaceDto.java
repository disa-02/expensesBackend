package com.expense.expense.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class SpaceDto {
    private Integer id;
    private String name;
    private String description;
    private Double balance ;
    private Date closeDate;   
}
