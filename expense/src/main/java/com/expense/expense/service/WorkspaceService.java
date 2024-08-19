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
import com.expense.expense.entity.WorkSpace;
import com.expense.expense.entity.UserEntity;
import com.expense.expense.mapper.WorkSpaceMapper;
import com.expense.expense.repository.WorkSpaceRepository;
import com.expense.expense.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkspaceService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkSpaceMapper workSpaceMapper;

    @Autowired
    WorkSpaceRepository workSpaceRepository;

    @Transactional
    public SpaceDto addworkSpace(Integer userId, SpaceAddDto workSpaceAddDto) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        WorkSpace workSpace = workSpaceMapper.WorkSpaceAddDtoToWorkSpace(workSpaceAddDto);
        workSpace.setUser(user);
        user.getAccounts()
            .forEach( account ->{
                AccountBalance accountBalance = new AccountBalance();
                accountBalance.setAccount(account);
                accountBalance.setSpace(workSpace);
                workSpace.getAccountsBalances().add(accountBalance);
            });
        user.addSpace(workSpace);
        WorkSpace newSpace = workSpaceRepository.save(workSpace);
        return workSpaceMapper.workSpaceToWorkSpaceDto(newSpace);
    }

    @Transactional
    public SpaceDto updateWorkSpace(Integer userId, SpaceUpdate workSpaceUpdate) {
        Integer spaceId = workSpaceUpdate.getId();
        WorkSpace workSpace = workSpaceRepository.findByIdAndUserId(spaceId,userId).orElseThrow(() -> new RuntimeException("Account not found in that user"));
        if(workSpaceUpdate.getName() != "")
            workSpace.setName(workSpaceUpdate.getName());
        if(workSpaceUpdate.getDescription() != "")
            workSpace.setDescription(workSpaceUpdate.getDescription());
        WorkSpace updateWorkSpace = workSpaceRepository.save(workSpace);
        return workSpaceMapper.workSpaceToWorkSpaceDto(updateWorkSpace);
    }

    public SpaceDto getWorkSpace(Integer userId, Integer spaceId) {
        WorkSpace workSpace = workSpaceRepository.findByIdAndUserId(spaceId,userId).orElseThrow(() -> new RuntimeException("Account not found in that user"));
        SpaceDto workSpaceDto = workSpaceMapper.workSpaceToWorkSpaceDto(workSpace);
        return workSpaceDto;

    }

    public List<SpaceDto> getWorkSpace(Integer userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<WorkSpace> spaces = workSpaceRepository.findByUserId(userId);
        return spaces
            .stream()
            .map(space -> workSpaceMapper.workSpaceToWorkSpaceDto(space))
            .collect(Collectors.toList())
            ;
    }

    @Transactional
    public void deleteWorkSpace(Integer userId, Integer spaceId) {
        WorkSpace workSpace = workSpaceRepository.findByIdAndUserId(spaceId,userId).orElseThrow(() -> new RuntimeException("Account not found in that user"));
        workSpace.getOperations()
            .stream()
            .forEach((operation) -> {
                Account account = operation.getAccount();
                account.setBalance(account.getBalance() - operation.getTransactionValue());
                account.setAvailableMoney(account.getAvailableMoney() - operation.calculateAvailableMoney());
            });
        UserEntity user = workSpace.getUser();
        user.delSpace(workSpace);
        userRepository.save(user);
    }


    
}
