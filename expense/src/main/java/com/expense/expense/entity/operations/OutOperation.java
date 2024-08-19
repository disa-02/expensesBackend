package com.expense.expense.entity.operations;

import com.expense.expense.entity.OperationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class OutOperation extends Operation {
    // @ManyToOne
    // private Category category;//solo en out??
    @Enumerated(value = EnumType.STRING)
    private OperationStatus status;

    @Override
    public Double getTransactionValue() {
        return -this.getAmount();
    }

    @Override
    public Double calculateAvailableMoney() {
        return -this.getAmount();
    }
    
}
