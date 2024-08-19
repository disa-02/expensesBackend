package com.expense.expense.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expense.expense.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByIdAndUserId(Integer id, Integer userId);

    List<Account> findByName(String name);

    List<Account> findByNameLike(String name);
}
