package com.expense.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.expense.expense.entity.Account;
import com.expense.expense.entity.operations.Operation;


public interface OperationRepository extends JpaRepository<Operation, Integer> {
    Optional<Operation> findByIdAndAccountId(Integer id, Integer accountId);
    
}
