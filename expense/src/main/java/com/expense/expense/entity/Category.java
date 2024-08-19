package com.expense.expense.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    // @OneToMany(mappedBy = "category")
    // private List<OutOperation> operations;

    // public void addOperation(OutOperation operation){
    //     operations.add(operation);
    // }

    // public void delOperation(OutOperation operation){
    //     operations.remove(operation);
    // }
}
