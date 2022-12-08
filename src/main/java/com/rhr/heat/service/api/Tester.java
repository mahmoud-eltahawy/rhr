package com.rhr.heat.service.api;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.topLayer.ShiftRepo;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.enums.EmployeePosition;
import com.rhr.heat.service.ProblemDetailMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Tester {
	private final EmployeeRepo employeeRepo;
	private final ShiftRepo shiftRepo;
	private final ProblemDetailMapper commonService;

	public void insertData() {
		Employee emp2 = new Employee(UUID.randomUUID(),"mahmoud","gamal","mohammed",EmployeePosition.worker,"mahmoud_gamal","1234");
		Employee emp3 = new Employee(UUID.randomUUID(),"taha","mohammed","ismaail",EmployeePosition.worker,"taha_mohammed","1234");
		Employee emp4 = new Employee(UUID.randomUUID(),"mohammed","gomaa","mohammed",EmployeePosition.worker,"mohammed_gomaa","1234");
		Employee emp5 = new Employee(UUID.randomUUID(),"ehab","hagag","saad",EmployeePosition.worker,"ehab_hagag","1234");
		
		employeeRepo.saveAll(List.of(emp2,emp3,emp4,emp5));
	}

	public Object emp() {
		return commonService.getCategoryMachines(shiftRepo.findLast(2).get(1).getProblems());
	}
}
