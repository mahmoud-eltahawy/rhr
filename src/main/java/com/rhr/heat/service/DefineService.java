package com.rhr.heat.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.enums.Pushable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefineService{
    private final MachineRepo machineRepo;
    private final ProblemRepo problemRepo;

    public List<String> noneHeadersCategories(){
        return machineRepo.findNoneHeadersCatagories();
    }

    public Boolean saveNewCategory(String category, Integer num){
        if(machineRepo.findByCatagory(category).isEmpty()){
            machineRepo.save(new Machine
                (UUID.randomUUID(),category, num));
            return true;
        }
        return false;
    }    

    public Boolean addNewMachine(String category){
        int len;
        if((len = machineRepo.findByCatagory(category).size()) != 0){
            machineRepo
            .save(new Machine(UUID.randomUUID(),category, ++len));
            return true;
        }
        return false;
    }    

    public List<Pushable> addNewProblem(String name,String description){
        return problemRepo.save(new Problem(name, description));        
    }    
}