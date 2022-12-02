package com.rhr.heat.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.CategoryRepo;
import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.entity.Category;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.enums.Pushable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefineService{
    private final MachineRepo machineRepo;
    private final ProblemRepo problemRepo;
    private final CategoryRepo categoryRepo;

    public List<String> hasMachinesCategoriesNames(){
        return categoryRepo.findHasMachinesNames();
    }

    public Boolean saveNewCategory(Category category){
        if(category.isPushable().isEmpty()){
            categoryRepo.save(category);
            if(!category.getHasMachines()){
                machineRepo.save(new Machine(UUID.randomUUID(),
                new Category(category.getName()), 0));
            }
            return true;
        }
        return false;
    }

    public Boolean addNewMachine(String catName){
        int len = machineRepo.findByCatagory(catName).size();
        if(machineRepo.save(new Machine(UUID.randomUUID(),
                new Category(catName), ++len)).isEmpty()){
            return true;
        }
        return false;
    }

    public List<Pushable> addNewProblem(String name,String description){
        return problemRepo.save(new Problem(name, description));        
    }    
}