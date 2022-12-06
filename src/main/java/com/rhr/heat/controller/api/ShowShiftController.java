package com.rhr.heat.controller.api;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.model.EmployeeName;
import com.rhr.heat.service.api.ShowShiftService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fetch/shift")
@RequiredArgsConstructor
public class ShowShiftController {
  private final ShowShiftService service;

  @PostMapping("/id")
  public ShiftId currentShift(@RequestParam("id")UUID ShiftIdId){
    return service.currentShift(ShiftIdId);
  }

  @PostMapping("/flow")
  public List<TotalFlow> allFlow(@RequestParam("id")UUID ShiftIdId){
    return service.currentShiftFlow(ShiftIdId);
  }

  @PostMapping("/notes")
  public List<Note> allNotes(@RequestParam("id")UUID ShiftIdId){
    return service.currentShiftNotes(ShiftIdId);
  }

  @PostMapping("/emps/names")
  public List<EmployeeName> allEmpsNames(@RequestParam("id")UUID ShiftIdId){
    return service.currentShiftEmployees(ShiftIdId);
  }

  @PostMapping("/temps")
  public List<Temperature> allTemps(@RequestParam("id")UUID ShiftIdId){
    return service.currentShiftTemperatures(ShiftIdId);
  }
  // Map<Category, Map<machine number, List<Problem Details>>>
  @PostMapping("/categories/numbers/problems/mapping")
  public Map<String, String> categoiesNumbersProblems(@RequestParam("id")UUID ShiftIdId){
    return	service.categoryNumberProblemMaping(ShiftIdId);
  }
}