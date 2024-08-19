package com.expense.expense.entity.operations;

import java.util.Date;

import com.expense.expense.entity.Account;
import com.expense.expense.entity.Space;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;
    private String description;
    private Double amount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Space space;


    public abstract Double getTransactionValue();
    public abstract Double calculateAvailableMoney();
}
