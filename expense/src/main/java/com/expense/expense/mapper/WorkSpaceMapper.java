package com.expense.expense.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.expense.expense.dto.SpaceAddDto;
import com.expense.expense.dto.SpaceDto;
import com.expense.expense.entity.Space;
import com.expense.expense.entity.WorkSpace;

@Component
public class WorkSpaceMapper {
    private ModelMapper modelMapper = new ModelMapper();
    
    public WorkSpaceMapper(){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    /* workSpace - WorkSpaceAddDto */
    public WorkSpace WorkSpaceAddDtoToWorkSpace(SpaceAddDto spaceAddDto){
        return modelMapper.map(spaceAddDto, WorkSpace.class);
    }

    public SpaceAddDto WorkSpaceToWorkSpaceAddDto(WorkSpace space){
        return modelMapper.map(space, SpaceAddDto.class);
    }

    /* WorkSpace - WorkSpaceDto */
    public SpaceDto workSpaceToWorkSpaceDto(Space space) {
        SpaceDto spaceDto = modelMapper.map(space, SpaceDto.class);
        spaceDto.setBalance(space.getBalance());
        return spaceDto;
    }
}