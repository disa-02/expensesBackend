package com.expense.expense.entity.operations;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class OutTransferOperation extends Operation {

    @OneToOne(optional = true)
    InTransferOperation transferOperation;

    @Override
    public Double getTransactionValue() {
        return -this.getAmount();
    }

    @Override
    public Double calculateAvailableMoney() {
        return -this.getAmount();
    }
    
}
