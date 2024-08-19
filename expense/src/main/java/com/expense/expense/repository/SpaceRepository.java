package com.expense.expense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expense.expense.entity.Space;

public interface SpaceRepository<T extends Space> extends JpaRepository<T, Integer> {
    Optional<T> findByIdAndUserId(Long id, Integer userId);
}
