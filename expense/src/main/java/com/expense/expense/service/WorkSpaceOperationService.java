package com.expense.expense.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expense.expense.dto.OperationAddDto;
import com.expense.expense.dto.OperationDto;
import com.expense.expense.dto.OperationUpdateDto;
import com.expense.expense.entity.Account;
import com.expense.expense.entity.AccountBalance;
import com.expense.expense.entity.WorkSpace;
import com.expense.expense.entity.operations.Operation;
import com.expense.expense.mapper.OperationMapper;
import com.expense.expense.repository.AccountBalanceRepository;
import com.expense.expense.repository.AccountRepository;
import com.expense.expense.repository.OperationRepository;
import com.expense.expense.repository.WorkSpaceRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkSpaceOperationService {

    @Autowired
    WorkSpaceRepository workSpaceRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OperationMapper operationMapper;

    // @Autowired
    // AccountOperationService accountOperationService;

    @Autowired
    AccountBalanceRepository accountBalanceRepository;

    @Autowired
    OperationRepository operationRepository;

    @Transactional
    public OperationDto addOperation(Integer userId, Integer spaceId, OperationAddDto operationDto) {
        WorkSpace workSpace = workSpaceRepository.findByIdAndUserId(spaceId, userId).orElseThrow(() -> new RuntimeException("Space not found in that user"));
        AccountBalance accountBalance = null;
        for(AccountBalance ab : workSpace.getAccountsBalances()){
            if(ab.getAccount().getId() == operationDto.getAccountId()){
                accountBalance = ab;
                break; 
            }
        }
        if(accountBalance == null)
            throw new RuntimeException("Account not found in that space");
        Operation operation = operationMapper.operationAddDtoToOperation(operationDto);
        operation.setSpace(workSpace);

        Account account = accountBalance.getAccount();
        //account.addOperation(operation);
        account.setBalance(account.getBalance() + operation.getTransactionValue());
        account.setAvailableMoney(account.getAvailableMoney() + operation.calculateAvailableMoney());

        operation.setAccount(account);

        accountBalance.setBalance(accountBalance.getBalance() + operation.getTransactionValue());
        workSpace.getOperations().add(operation);

        Operation newOperation = operationRepository.save(operation);
        return operationMapper.operationToOperationDto(newOperation);
    }
    
    @Transactional
    public void updateOperation(Integer userId, Integer spaceId, OperationUpdateDto operationDto) {
        WorkSpace workSpace = workSpaceRepository.findByIdAndUserId(spaceId, userId).orElseThrow(() -> new RuntimeException("Space not found in that user"));
        Operation operation = findOperation(workSpace, operationDto.getId());
        if(operationDto.getName() != null)
            operation.setName(operationDto.getName());
        if(operation.getDescription() !=null)
            operation.setDescription(operationDto.getDescription());
        operationRepository.save(operation);
    }

    public List<OperationDto> getOperations(Integer userId, Integer spaceId) {
        WorkSpace workSpace = workSpaceRepository.findByIdAndUserId(spaceId, userId).orElseThrow(() -> new RuntimeException("Space not found in that user"));
        return workSpace.getOperations()
            .stream()
            .map( operation -> operationMapper.operationToOperationDto(operation))
            .collect(Collectors.toList())
            ;
    }


    private Operation findOperation(WorkSpace workSpace, Integer operationId){
        Operation operation = null;
        for(Operation op : workSpace.getOperations()){
            if(op.getId().equals(operationId)){
                operation = op;
                break;
            }
        }
        if(operation == null){
            throw new RuntimeException("Operation not found in that space");
        }
        return operation;
    }

    private AccountBalance findAccountBalance(WorkSpace workSpace, Integer accountId){
        for(AccountBalance ab : workSpace.getAccountsBalances()){
            if(ab.getAccount().getId() == accountId){
                return ab;
            }
        }
        return null;
    }

    @Transactional
    public void deleteOperation(Integer userId, Integer spaceId, Integer operationId) {
        WorkSpace workSpace = workSpaceRepository.findByIdAndUserId(spaceId, userId).orElseThrow(() -> new RuntimeException("Space not found in that user"));
        Operation operation = findOperation(workSpace, operationId);
        Account account = operation.getAccount();
        AccountBalance accountBalance = findAccountBalance(workSpace, account.getId());
        if(accountBalance == null){
            throw new RuntimeException("Unexpected error, the account must exist in some accountBalance");
        }
        accountBalance.setBalance(accountBalance.getBalance() - operation.getTransactionValue());
        account.setBalance(account.getBalance() - operation.getTransactionValue());
        account.setAvailableMoney(account.getAvailableMoney() - operation.calculateAvailableMoney());

        // accountOperationService.delOperation(account, operation);
        // spaceOperation.getOperations().remove(operation);

        workSpace.getOperations().removeIf(operationn -> operationn.getId().equals(operationId));
        workSpaceRepository.save(workSpace);
        // operationRepository.deleteById(operationId);
    }

    public void setBalanceOnDeleteOperation(Operation operation, Integer accountId){
        WorkSpace workSpace = (WorkSpace)operation.getSpace();
        if(workSpace != null){
            AccountBalance accountBalance = findAccountBalance(workSpace, accountId);
            if(accountBalance != null){
                accountBalance.setBalance(accountBalance.getBalance() - operation.getTransactionValue());
                workSpaceRepository.save(workSpace);
            }
        }

    }
    
}
