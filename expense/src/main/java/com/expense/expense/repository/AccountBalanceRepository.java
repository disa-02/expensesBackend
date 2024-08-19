package com.expense.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expense.expense.entity.AccountBalance;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Integer> {
    
}
