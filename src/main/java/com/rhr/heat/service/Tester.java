package com.rhr.heat.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.AtmsShiftStatusRepo;
import com.rhr.heat.dao.DrayersShiftStatusRepo;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.KilensShiftStatusRepo;
import com.rhr.heat.dao.ProblemDetailsRepo;
import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.dao.WorkdayRepo;
import com.rhr.heat.model.AtmsShiftStatus;
import com.rhr.heat.model.DrayersShiftStatus;
import com.rhr.heat.model.Employee;
import com.rhr.heat.model.EmployeePosition;
import com.rhr.heat.model.KilensShiftStatus;
import com.rhr.heat.model.Problem;
import com.rhr.heat.model.ProblemDetail;
import com.rhr.heat.model.Shift;
import com.rhr.heat.model.WorkDay;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Tester {
	private final AtmsShiftStatusRepo atmsShiftStatusRepo;
	private final DrayersShiftStatusRepo drayersShiftStatusRepo;
	private final KilensShiftStatusRepo kilensShiftStatusRepo;
	private final ProblemDetailsRepo problemDetailsRepo;
	private final EmployeeRepo employeeRepo;
	private final ShiftRepo shiftRepo;
	private final WorkdayRepo workdayRepo;
	
	public void insertday() {
		ProblemDetail pd1 = new ProblemDetail(null, Problem.p1, 2.5);
		ProblemDetail pd2 = new ProblemDetail(null, Problem.p2, 1.5);
		ProblemDetail pd3 = new ProblemDetail(null, Problem.p3, 0.5);
		
		Employee emp = new Employee(null,"mahmoud","gamal","mohammed",EmployeePosition.worker);
		
		employeeRepo.save(emp);
		
		problemDetailsRepo.save(pd1);
		problemDetailsRepo.save(pd2);
		problemDetailsRepo.save(pd3);
		
		KilensShiftStatus kilens = new KilensShiftStatus();
		kilens.setKiln1(List.of(pd1,pd2,pd3));
		kilens.setKiln2(List.of(pd2,pd3));
		kilens.setKiln3(List.of(pd1,pd3));
		kilens.setKiln4(List.of(pd1,pd2));
		kilens.setKiln5(List.of(pd1));
		
		kilensShiftStatusRepo.save(kilens);
		
		DrayersShiftStatus drayers = new DrayersShiftStatus();
		drayers.setDrayer1(List.of(pd1,pd2,pd3));
		drayers.setDrayer2(List.of(pd2,pd3));
		drayers.setDrayer3(List.of(pd1,pd3));
		drayers.setDrayer4(List.of(pd1,pd2));
		drayers.setDrayer5(List.of(pd3));
		drayers.setDrayer6(List.of(pd1));
		drayers.setDrayer7(List.of(pd2));
		
		drayersShiftStatusRepo.save(drayers);
		
		AtmsShiftStatus atms = new AtmsShiftStatus();
		atms.setAtm1(List.of(pd2,pd3));
		atms.setAtm1(List.of(pd1));
		
		atmsShiftStatusRepo.save(atms);
		
		Shift shift3 = new Shift();
		shift3.setKilens(kilens);
		shift3.setDrayers(drayers);
		shift3.setAtms(atms);
		shift3.setEmployees(List.of(emp));
		
		shiftRepo.save(shift3);
		
		WorkDay workDay = new WorkDay();
		workDay.setShift1(null);
		workDay.setShift2(null);
		workDay.setShift3(shift3);
		workDay.setDate(new Date());
		
		workdayRepo.save(workDay);
		
	}
}
