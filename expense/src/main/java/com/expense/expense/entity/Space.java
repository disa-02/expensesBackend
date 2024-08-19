package com.expense.expense.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String description;
    @OneToMany (mappedBy = "space",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AccountBalance> accountsBalances = new ArrayList<>();
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;
    @ManyToOne
    private UserEntity user;

    public Double getBalance(){
        Double balance = 0.0;
        for(AccountBalance accountBalance: accountsBalances){
            balance += accountBalance.getBalance();
        }
        return balance;
    }
}
