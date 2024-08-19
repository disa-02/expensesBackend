package com.expense.expense.repository;

import org.springframework.stereotype.Repository;

import com.expense.expense.entity.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);
    
}
