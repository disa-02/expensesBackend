package com.expense.expense.entity;

import java.util.Date;
import java.util.List;

import com.expense.expense.entity.operations.OutOperation;

import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class InstallmentExpense extends OutOperation {

    private Integer installmentAmount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    private Integer intervalsDays;
    private List<Double> interests;
    
    @Override
    public Double getTransactionValue() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateExpense'");
    }

    @Override
    public Double calculateAvailableMoney() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateAvailableMoney'");
    }
    
}
