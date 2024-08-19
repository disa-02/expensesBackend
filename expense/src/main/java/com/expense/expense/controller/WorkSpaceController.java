package com.expense.expense.controller;

import org.springframework.web.bind.annotation.RestController;

import com.expense.expense.dto.SpaceAddDto;
import com.expense.expense.dto.SpaceDto;
import com.expense.expense.dto.SpaceUpdate;
import com.expense.expense.dto.validator.SpaceValidator;
import com.expense.expense.service.WorkspaceService;

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
@RequestMapping("/workSpace")
@PreAuthorize("(#userId == principal.id)")
@CrossOrigin
public class WorkSpaceController {
    
    @Autowired
    SpaceValidator workSpaceValidator;

    @Autowired
    WorkspaceService workSpaceService;



    @PostMapping("/{userId}")
    public ResponseEntity<SpaceDto> addworkSpace(@PathVariable Integer userId,@RequestBody SpaceAddDto workSpaceAddDto) {
        workSpaceValidator.validateSpaceAddDto(workSpaceAddDto);
        SpaceDto workSpaceDto = workSpaceService.addworkSpace(userId,workSpaceAddDto);
        return new ResponseEntity<>(workSpaceDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<SpaceDto> updateWorkSpace(@PathVariable Integer userId, @RequestBody SpaceUpdate workSpaceUpdate) {
        workSpaceValidator.validateSpaceUpdateDto(workSpaceUpdate);
        SpaceDto workSpaceDto = workSpaceService.updateWorkSpace(userId, workSpaceUpdate);
        return new ResponseEntity<>(workSpaceDto,HttpStatus.OK); 
    }

    @GetMapping
    public ResponseEntity<SpaceDto> getWorkSpace(@RequestParam Integer userId, @RequestParam Integer spaceId) {
        SpaceDto workSpaceDto = workSpaceService.getWorkSpace(userId,spaceId);
        return new ResponseEntity<>(workSpaceDto,HttpStatus.OK); 
    }

    @GetMapping("/list")
    public ResponseEntity<List<SpaceDto>> getWorkSpace(@RequestParam Integer userId) {
        List<SpaceDto> workSpaceDto = workSpaceService.getWorkSpace(userId);
        return new ResponseEntity<>(workSpaceDto,HttpStatus.OK); 
    }

    @DeleteMapping("/{userId}/{accountId}")
    public ResponseEntity<?> deleteWorkSpace(@PathVariable Integer userId, @PathVariable Integer accountId){
        workSpaceService.deleteWorkSpace(userId,accountId);
        return new ResponseEntity<>(HttpStatus.OK); 
    }
    
    
}
