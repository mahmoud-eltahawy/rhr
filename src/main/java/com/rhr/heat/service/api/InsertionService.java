package com.rhr.heat.service.api;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.topLayer.ShiftRepo;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.topLayer.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsertionService {
	private final ShiftRepo shiftIdRepo;
	private final EmployeeRepo employeeRepo;
	
	public Shift saveShift(Shift shift) {
		shiftIdRepo.save(shift);
		return shift;
	}
	
	public Employee register(Employee emp) {
		employeeRepo.save(emp);
		return emp;
	}
}
