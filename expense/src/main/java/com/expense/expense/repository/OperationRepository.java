package com.expense.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.expense.expense.entity.Account;
import com.expense.expense.entity.operations.Operation;
import com.expense.expense.entity.operations.OutOperation;


public interface OperationRepository extends JpaRepository<Operation, Integer> {
    Optional<Operation> findByIdAndAccountId(Integer id, Integer accountId);
    @Query( "SELECT SUM(o.amount), FUNCTION('YEAR', o.date), FUNCTION('MONTH', o.date) " +
            "FROM OutOperation o " +
            "WHERE o.date >= :startDate AND o.date <= :endDate " +  
            "AND o.account.user.id = :id " +  
            "GROUP BY FUNCTION('YEAR', o.date), FUNCTION('MONTH', o.date)")
    List<Object[]> findTotalOutOperationsGroupedByDate(LocalDateTime startDate, LocalDateTime endDate, Integer id);

}
