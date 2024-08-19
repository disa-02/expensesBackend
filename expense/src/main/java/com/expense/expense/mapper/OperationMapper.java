package com.expense.expense.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.weaver.ast.Instanceof;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.expense.expense.dto.OperationAddDto;
import com.expense.expense.dto.OperationDto;
import com.expense.expense.dto.OperationType;
import com.expense.expense.entity.operations.InOperation;
import com.expense.expense.entity.operations.InTransferOperation;
import com.expense.expense.entity.operations.Operation;
import com.expense.expense.entity.operations.OutOperation;
import com.expense.expense.entity.operations.OutTransferOperation;
import com.expense.expense.entity.operations.SavingOperation;

@Component
public class OperationMapper {
       
    private ModelMapper modelMapper = new ModelMapper();
    
    public OperationMapper(){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    /* Operation - OperationAddDto */
    public Operation operationAddDtoToOperation(OperationAddDto operationDto){
        Operation operation = null;
        if(operationDto.getType() == OperationType.IN)
            operation = operationAddDtoToInOperation(operationDto);
        else if(operationDto.getType() == OperationType.OUT){
            operation = operationAddDtoToOutOperation(operationDto);
        }
        else if(operationDto.getType() == OperationType.SAVE){
            operation = operationAddDtoToSavingOperation(operationDto);
        }
        else if(operationDto.getType() == OperationType.OUTTRANSFER){
            operation = operationAddDtoToOutTransfeOperation(operationDto);
        }
        else if(operationDto.getType() == OperationType.INTRANSFER){
            operation = operationAddDtoToInTransfeOperation(operationDto);
        }
            // Convertir el String a Date
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date date = formatter.parse(operationDto.getDate());
            operation.setDate(date);
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar excepción
        }
        return operation;
    }


    /* InOperation - OperationAddDto */
    public InOperation operationAddDtoToInOperation(OperationAddDto operationDto){
        return modelMapper.map(operationDto, InOperation.class);
    }

    public OperationAddDto inOperationToOperationAddDto(InOperation inOperation){
        return modelMapper.map(inOperation, OperationAddDto.class);
    }

    /* OutOperation - OperationAddDto */
    public OutOperation operationAddDtoToOutOperation(OperationAddDto operationDto){
        return modelMapper.map(operationDto, OutOperation.class);
    }

    public OperationAddDto outOperationToOperationAddDto(OutOperation outOperation){
        return modelMapper.map(outOperation, OperationAddDto.class);
    }


    public OperationAddDto savingOperationToOperationAddDto(SavingOperation savingOperation){
        return modelMapper.map(savingOperation, OperationAddDto.class);
    }

    /* InOperation - OperationDto */
    public InOperation operationDtoToInOperation(OperationDto operationDto){
        return modelMapper.map(operationDto, InOperation.class);
    }

    public OperationDto inOperationToOperationDto(InOperation inOperation){
        return modelMapper.map(inOperation, OperationDto.class);
    }
    /* SavingOperation - OperationDto */
    public SavingOperation operationDtoToSavingOperation(OperationDto operationDto){
        return modelMapper.map(operationDto, SavingOperation.class);
    }

    public OperationDto savingOperationToOperationDto(SavingOperation savingOperation){
        return modelMapper.map(savingOperation, OperationDto.class);
    }

    /* OutOperation - OperationDto */
    public OutOperation operationDtoToOutOperation(OperationDto operationDto){
        return modelMapper.map(operationDto, OutOperation.class);
    }

    public OperationDto outOperationToOperationDto(OutOperation outOperation){
        return modelMapper.map(outOperation, OperationDto.class);
    }

    /* Operation - OperationDto */
    public Operation operationDtoToOperation(OperationDto operationDto){
        return modelMapper.map(operationDto, Operation.class);
    }

    public OperationDto operationToOperationDto(Operation operation){
        OperationDto operationDto = modelMapper.map(operation, OperationDto.class);
        operationDto.setType(operation.getClass().getSimpleName());
        operationDto.setAccountName(operation.getAccount().getName());
        operationDto.setId(operation.getId());
        return operationDto;
    }

    public SavingOperation operationAddDtoToSavingOperation(OperationAddDto operationDto){
        return modelMapper.map(operationDto, SavingOperation.class);
    }

    public InTransferOperation operationAddDtoToInTransfeOperation(OperationAddDto operationDto){
        return modelMapper.map(operationDto, InTransferOperation.class);
    }

    public OutTransferOperation operationAddDtoToOutTransfeOperation(OperationAddDto operationDto){
        return modelMapper.map(operationDto, OutTransferOperation.class);
    }


}
