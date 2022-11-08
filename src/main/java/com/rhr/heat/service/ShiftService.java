package com.rhr.heat.service;

import java.sql.Date;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.dao.ShiftIdRepo;
import com.rhr.heat.model.Day;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShiftService {
	public final ShiftIdRepo shiftIdRepo;
	public final EmployeeRepo employeeRepo;
	public final ProblemRepo problemRepo;
	
	public TreeMap<Date ,Day> pickLastWeeks(Integer weekNum){
		return Day.getDays(shiftIdRepo.findFromTo(weekNum * 21, 21));
	}
	
	public TreeMap<Date ,Day> pickLastdays(Integer days){
		return Day.getDays(shiftIdRepo.findLast(days * 3));
	}
}