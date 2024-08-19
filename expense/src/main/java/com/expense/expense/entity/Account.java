package com.expense.expense.entity;

import java.util.ArrayList;
import java.util.List;

import com.expense.expense.entity.operations.Operation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    
    private String name;
    private String description;
    private Double balance = 0.0;
    private Double availableMoney = 0.0;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Operation> operations = new ArrayList<>();
    @ManyToOne
    private UserEntity user;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account",cascade = CascadeType.ALL,orphanRemoval = true)
    List<AccountBalance> accountBalance;

    public void addOperation(Operation operation){
        operations.add(operation);
    }
}
