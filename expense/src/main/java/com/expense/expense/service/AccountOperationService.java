package com.expense.expense.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expense.expense.dto.ExpensesByMonthDto;
import com.expense.expense.dto.OperationAddDto;
import com.expense.expense.dto.OperationDto;
import com.expense.expense.dto.OperationType;
import com.expense.expense.dto.OperationUpdateDto;
import com.expense.expense.dto.TransferDto;
import com.expense.expense.dto.TransferOperationDto;
import com.expense.expense.dto.validator.OperationDtoValidator;
import com.expense.expense.entity.Account;
import com.expense.expense.entity.WorkSpace;
import com.expense.expense.entity.operations.InTransferOperation;
import com.expense.expense.entity.operations.Operation;
import com.expense.expense.entity.operations.OutOperation;
import com.expense.expense.entity.operations.OutTransferOperation;
import com.expense.expense.mapper.OperationMapper;
import com.expense.expense.repository.AccountRepository;
import com.expense.expense.repository.OperationRepository;
import com.expense.expense.repository.WorkSpaceRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountOperationService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OperationMapper operationMapper;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    WorkSpaceRepository workSpaceRepository;

    @Autowired
    WorkSpaceOperationService workSpaceOperationService;


    public void addOperation(Account account, Operation operation){
        operation.setAccount(account);
        account.addOperation(operation);
        account.setBalance(account.getBalance() + operation.getTransactionValue());
        account.setAvailableMoney(account.getAvailableMoney() + operation.calculateAvailableMoney());
    }

    private Operation doTransaction(OperationAddDto operationDto, Account account){
        Operation operation = operationMapper.operationAddDtoToOperation(operationDto);
        addOperation(account,operation);
        Operation operationUpdated = operationRepository.save(operation);
        return operationUpdated;
    }

    @Transactional
    public OperationDto moneyTransaction(Integer userId, OperationAddDto operationDto) {
        Integer accountId = operationDto.getAccountId();
        Account account = accountRepository.findByIdAndUserId(accountId, userId).orElseThrow(() -> new RuntimeException("Account not found in that user"));
        Operation operationUpdated = doTransaction(operationDto, account);
        return operationMapper.operationToOperationDto(operationUpdated);
    }

    @Transactional
    public void updateOperation(Integer userId, Integer accountId, OperationUpdateDto operationDto) throws ParseException {
        Operation operation = operationRepository.findByIdAndAccountId(operationDto.getId(), accountId).orElseThrow(() -> new RuntimeException("Operation not found in that account"));
        Integer operationUserId = operation.getAccount().getUser().getId();
        if(userId != operationUserId){
            throw new RuntimeException("Account not found in that user");
        }
        if(operationDto.getName() != ""){
            operation.setName(operationDto.getName());
        }
        if(operationDto.getDescription() != ""){
            operation.setDescription(operationDto.getDescription());
        }
        // if(operationDto.getDate() != ""){
        //     String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
        //     SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        //     Date date = formatter.parse(operationDto.getDate());
        //     operation.setDate(date);
        // }
        operationRepository.save(operation);
    } 
    
    public void delOperation(Account account, Operation operation){
        account.getOperations().removeIf(op -> op.getId().equals(operation.getId()));
        // Como se esta borrando, se hace la operacion inversa
        account.setBalance(account.getBalance() - operation.getTransactionValue());
        account.setAvailableMoney(account.getAvailableMoney() - operation.calculateAvailableMoney());

    }

    @Transactional
    public void deleteOperation(Integer userId, Integer accountId, Integer operationId){
        Operation operation = operationRepository.findByIdAndAccountId(operationId, accountId).orElseThrow(() -> new RuntimeException("Operation not found in that account"));
        Account account = operation.getAccount();
        Integer operationUserId = account.getUser().getId();
        if(userId != operationUserId){
            throw new RuntimeException("Account not found in that user");
        }
        workSpaceOperationService.setBalanceOnDeleteOperation(operation,accountId);
        delOperation(account, operation);

        if(operation instanceof InTransferOperation){
            InTransferOperation inTransferOperation = (InTransferOperation)operation;
            OutTransferOperation outTransferOperation = inTransferOperation.getTransferOperation();
            if(outTransferOperation != null){
                Account outAccount = outTransferOperation.getAccount(); 
                delOperation(outAccount, outTransferOperation);
                outTransferOperation.setTransferOperation(null);

            }
            inTransferOperation.setTransferOperation(null);
            
        }
        if(operation instanceof OutTransferOperation){
            OutTransferOperation outTransferOperation = (OutTransferOperation)operation;
            InTransferOperation inTransferOperation = outTransferOperation.getTransferOperation();
            
            if(inTransferOperation != null){
                Account inAccount = inTransferOperation.getAccount(); 
                delOperation(inAccount, inTransferOperation);
                inTransferOperation.setTransferOperation(null);
            }
            outTransferOperation.setTransferOperation(null);
            
            // accountRepository.save(inAccount);
        }
        accountRepository.save(account);
        
    }

    public List<OperationDto> getOperations(Integer userId, Integer accountId) {
        Account account = accountRepository.findByIdAndUserId(accountId, userId).orElseThrow(() -> new RuntimeException("Account not found in that user"));
        return account.getOperations()
                .stream()
                .map(operation -> operationMapper.operationToOperationDto(operation))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OperationDto> transferMoney(Integer userId, TransferOperationDto transferOperationDto) {
        Integer sourceAccountId = transferOperationDto.getSourceAccountId();
        Integer destinationAccountId = transferOperationDto.getDestinationAccountId();
        Double amount = transferOperationDto.getAmount();

        Account sourceaAccount = accountRepository.findByIdAndUserId(sourceAccountId, userId).orElseThrow(() -> new RuntimeException("Account not found in that user"));
        Account destinationAccount = accountRepository.findByIdAndUserId(destinationAccountId, userId).orElseThrow(() -> new RuntimeException("Account not found in that user"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String now = formatter.format(new Date());

        OperationAddDto sourceOperationAddDto = new OperationAddDto();
        sourceOperationAddDto.setAccountId(sourceAccountId);
        sourceOperationAddDto.setAmount(amount);
        sourceOperationAddDto.setDate(now);
        sourceOperationAddDto.setName("Fund Transfer Sending");
        sourceOperationAddDto.setDescription("Sending to account " + destinationAccount.getName());
        sourceOperationAddDto.setType(OperationType.OUTTRANSFER);
        OutTransferOperation sourceOperation = (OutTransferOperation)operationMapper.operationAddDtoToOperation(sourceOperationAddDto);
        addOperation(sourceaAccount,sourceOperation);
        // OutTransferOperation sourceOperation = doTransaction(sourceOperationAddDto, sourceaAccount);

        OperationAddDto destinationOperationAddDto = new OperationAddDto();
        destinationOperationAddDto.setAccountId(destinationAccountId);
        destinationOperationAddDto.setAmount(amount);
        destinationOperationAddDto.setDate(now);
        destinationOperationAddDto.setName("Fund Transfer Receipt");
        destinationOperationAddDto.setDescription("Receiving from account " + sourceaAccount.getName());
        destinationOperationAddDto.setType(OperationType.INTRANSFER);
        InTransferOperation destinationOperation = (InTransferOperation)operationMapper.operationAddDtoToOperation(destinationOperationAddDto);
        addOperation(destinationAccount,destinationOperation);
        // InTransferOperation destinationOperation = doTransaction(destinationOperationAddDto, destinationAccount);

        sourceOperation.setTransferOperation(destinationOperation);
        destinationOperation.setTransferOperation(sourceOperation);

        sourceOperation = operationRepository.save(sourceOperation);
        destinationOperation = operationRepository.save(destinationOperation);


        
        List<OperationDto> operations = new ArrayList<>();
        operations.add(operationMapper.operationToOperationDto(sourceOperation));
        operations.add(operationMapper.operationToOperationDto(destinationOperation));

        return operations;
    }

    private String getMonth(Integer number){
        String name = "";
        switch (number) {
            case 1:
                name = "January";
                break;
            case 2:
                name = "February";
                break;
            case 3:
                name = "March";
                break;
            case 4:
                name = "April";
                break;
            case 5:
                name = "May";
                break;
            case 6:
                name = "June";
                break;
            case 7:
                name = "July";
                break;
            case 8:
                name = "August";
                break;
            case 9:
                name = "September";
                break;
            case 10:
                name = "October";
                break;
            case 11:
                name = "November";
                break;
            case 12:
                name = "December";
                break;
            default:
                break;
        }
        return name;
    }

    public List<ExpensesByMonthDto> findExpenseByMonth(Integer amountMonths, Integer id){
        List<ExpensesByMonthDto> totalOutOperationsByDate = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate beginDate = now.minusMonths(amountMonths);
        
        LocalDateTime beginDateTime = beginDate.minusDays(1).atStartOfDay();
        LocalDateTime endDateTime = now.plusDays(1).atStartOfDay();
        List<Object[]> results = operationRepository.findTotalOutOperationsGroupedByDate(beginDateTime,endDateTime,id);
        
         for (Object[] result : results) {
            Integer year = (Integer)result[1];
            String month = getMonth((Integer)result[2]);
            Double sum = (Double) result[0];
            ExpensesByMonthDto expense = new ExpensesByMonthDto();
            expense.setDate(year + "-" + month);
            expense.setExpense(sum);
            totalOutOperationsByDate.add(expense); 
        }

        return totalOutOperationsByDate;
    }

}
