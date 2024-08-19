package com.expense.expense.entity;

import java.util.ArrayList;
import java.util.List;

import com.expense.expense.entity.operations.SavingOperation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class SavingSpace extends Space {
    @OneToMany (mappedBy = "space", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SavingOperation> operations = new ArrayList<>();
}
