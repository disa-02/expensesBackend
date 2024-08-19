package com.expense.expense.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expense.expense.dto.SpaceAddDto;
import com.expense.expense.dto.SpaceDto;
import com.expense.expense.dto.SpaceUpdate;
import com.expense.expense.entity.Account;
import com.expense.expense.entity.AccountBalance;
import com.expense.expense.entity.SavingSpace;
import com.expense.expense.entity.UserEntity;
import com.expense.expense.mapper.SavingSpaceMapper;
import com.expense.expense.mapper.WorkSpaceMapper;
import com.expense.expense.repository.SavingSpaceRepository;
import com.expense.expense.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class SavingSpaceService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SavingSpaceMapper savingSpaceMapper;

    @Autowired
    SavingSpaceRepository savingSpaceRepository;

    @Transactional
    public SpaceDto addSavingSpace(Integer userId, SpaceAddDto savingSpaceAddDto) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        SavingSpace savingSpace = savingSpaceMapper.SavingSpaceAddDtoToSavingSpace(savingSpaceAddDto);
        savingSpace.setUser(user);
        user.getAccounts()
            .forEach( account ->{
                AccountBalance accountBalance = new AccountBalance();
                accountBalance.setAccount(account);
                accountBalance.setSpace(savingSpace);
                savingSpace.getAccountsBalances().add(accountBalance);
            });
        user.addSpace(savingSpace);
        SavingSpace newSpace = savingSpaceRepository.save(savingSpace);
        return savingSpaceMapper.savingSpaceToSavingSpaceDto(newSpace);
    }

    @Transactional
    public SpaceDto updateSavingSpace(Integer userId, SpaceUpdate savingSpaceUpdate) {
        Integer spaceId = savingSpaceUpdate.getId();
        SavingSpace savingSpace = savingSpaceRepository.findByIdAndUserId(spaceId,userId).orElseThrow(() -> new RuntimeException("Account not found in that user"));
        if(savingSpaceUpdate.getName() != "")
            savingSpace.setName(savingSpaceUpdate.getName());
        if(savingSpaceUpdate.getDescription() != "")
            savingSpace.setDescription(savingSpaceUpdate.getDescription());
        SavingSpace updateSavingSpace = savingSpaceRepository.save(savingSpace);
        return savingSpaceMapper.savingSpaceToSavingSpaceDto(updateSavingSpace);
    }

    public SpaceDto getSavingSpace(Integer userId, Integer spaceId) {
        SavingSpace savingSpace = savingSpaceRepository.findByIdAndUserId(spaceId,userId).orElseThrow(() -> new RuntimeException("Account not found in that user"));
        SpaceDto savingSpaceDto = savingSpaceMapper.savingSpaceToSavingSpaceDto(savingSpace);
        return savingSpaceDto;

    }

    public List<SpaceDto> getSavingSpace(Integer userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<SavingSpace> spaces = savingSpaceRepository.findByUserId(userId);
        return spaces
            .stream()
            .map(space -> savingSpaceMapper.savingSpaceToSavingSpaceDto(space))
            .collect(Collectors.toList())
            ;
    }

    @Transactional
    public void deleteSavingSpace(Integer userId, Integer spaceId) {
        SavingSpace savingSpace = savingSpaceRepository.findByIdAndUserId(spaceId,userId).orElseThrow(() -> new RuntimeException("Account not found in that user"));
        savingSpace.getOperations()
            .stream()
            .forEach((operation) -> {
                Account account = operation.getAccount();
                account.setBalance(account.getBalance() - operation.getTransactionValue());
                account.setAvailableMoney(account.getAvailableMoney() - operation.calculateAvailableMoney());
            });
        UserEntity user = savingSpace.getUser();
        user.delSpace(savingSpace);
        userRepository.save(user);
    }


    
}
