package com.expense.expense.controller;

import org.springframework.web.bind.annotation.RestController;

import com.expense.expense.dto.SpaceAddDto;
import com.expense.expense.dto.SpaceDto;
import com.expense.expense.dto.SpaceUpdate;
import com.expense.expense.dto.validator.SpaceValidator;
import com.expense.expense.service.SavingSpaceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/savingSpace")
@PreAuthorize("(#userId == principal.id)")
@CrossOrigin
public class SavingSpaceController {
    
    @Autowired
    SpaceValidator savingSpaceValidator;

    @Autowired
    SavingSpaceService savingSpaceService;



    @PostMapping("/{userId}")
    public ResponseEntity<SpaceDto> addsavingSpace(@PathVariable Integer userId,@RequestBody SpaceAddDto savingSpaceAddDto) {
        savingSpaceValidator.validateSpaceAddDto(savingSpaceAddDto);
        SpaceDto savingSpaceDto = savingSpaceService.addSavingSpace(userId,savingSpaceAddDto);
        return new ResponseEntity<>(savingSpaceDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<SpaceDto> updatesavingSpace(@PathVariable Integer userId, @RequestBody SpaceUpdate savingSpaceUpdate) {
        savingSpaceValidator.validateSpaceUpdateDto(savingSpaceUpdate);
        SpaceDto savingSpaceDto = savingSpaceService.updateSavingSpace(userId, savingSpaceUpdate);
        return new ResponseEntity<>(savingSpaceDto,HttpStatus.OK); 
    }

    @GetMapping
    public ResponseEntity<SpaceDto> getsavingSpace(@RequestParam Integer userId, @RequestParam Integer spaceId) {
        SpaceDto savingSpaceDto = savingSpaceService.getSavingSpace(userId,spaceId);
        return new ResponseEntity<>(savingSpaceDto,HttpStatus.OK); 
    }

    @GetMapping("/list")
    public ResponseEntity<List<SpaceDto>> getsavingSpace(@RequestParam Integer userId) {
        List<SpaceDto> savingSpaceDto = savingSpaceService.getSavingSpace(userId);
        return new ResponseEntity<>(savingSpaceDto,HttpStatus.OK); 
    }

    @DeleteMapping("/{userId}/{accountId}")
    public ResponseEntity<?> deletesavingSpace(@PathVariable Integer userId, @PathVariable Integer accountId){
        savingSpaceService.deleteSavingSpace(userId,accountId);
        return new ResponseEntity<>(HttpStatus.OK); 
    }
    
    
}
