package com.expense.expense.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expense.expense.entity.SavingSpace;
import com.expense.expense.entity.WorkSpace;

@Repository
public interface WorkSpaceRepository extends JpaRepository<WorkSpace, Integer> {

    Optional<WorkSpace> findByIdAndUserId(Integer spaceId, Integer userId);

    List<WorkSpace> findByUserId(Integer userId);

    
}
