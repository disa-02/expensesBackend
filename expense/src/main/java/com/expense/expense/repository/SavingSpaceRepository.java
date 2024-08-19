package com.expense.expense.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expense.expense.entity.SavingSpace;

@Repository
public interface SavingSpaceRepository extends JpaRepository<SavingSpace, Integer> {

    Optional<SavingSpace> findByIdAndUserId(Integer spaceId, Integer userId);

    List<SavingSpace> findByUserId(Integer userId);

    
}
