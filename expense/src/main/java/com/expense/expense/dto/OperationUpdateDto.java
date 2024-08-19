package com.expense.expense.dto;

import lombok.Data;

@Data
public class OperationUpdateDto {
    private Integer id;
    private String name;
    private String description;
    //private String date; GUARDA CON ESTO, puede modificar el balance si no se encuentra en el rango
}
