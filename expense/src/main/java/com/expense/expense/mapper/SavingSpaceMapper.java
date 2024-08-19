package com.expense.expense.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.expense.expense.dto.SpaceAddDto;
import com.expense.expense.dto.SpaceDto;
import com.expense.expense.entity.SavingSpace;
import com.expense.expense.entity.Space;

@Component
public class SavingSpaceMapper {
    private ModelMapper modelMapper = new ModelMapper();
    
    public SavingSpaceMapper(){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    /* savingSpace - SavingSpaceAddDto */
    public SavingSpace SavingSpaceAddDtoToSavingSpace(SpaceAddDto spaceAddDto){
        return modelMapper.map(spaceAddDto, SavingSpace.class);
    }

    public SpaceAddDto SavingSpaceToSavingSpaceAddDto(SavingSpace space){
        return modelMapper.map(space, SpaceAddDto.class);
    }

    /* SavingSpace - SavingSpaceDto */
    public SpaceDto savingSpaceToSavingSpaceDto(Space space) {
        SpaceDto spaceDto = modelMapper.map(space, SpaceDto.class);
        spaceDto.setBalance(space.getBalance());
        return spaceDto;
    }
}