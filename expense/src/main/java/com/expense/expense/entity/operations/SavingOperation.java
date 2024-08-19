package com.expense.expense.entity.operations;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class SavingOperation extends OutOperation {

    @Override
    public Double getTransactionValue() {
        return 0.0;
    }
}
