package com.rhr.heat.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
		ProblemDetail pd4 = new ProblemDetail(null, Problem.p4, 2.0);
		
		problemDetailsRepo.save(pd1);
		problemDetailsRepo.save(pd2);
		problemDetailsRepo.save(pd3);
		problemDetailsRepo.save(pd4);
		
		Employee emp1 = new Employee(null,"taha","mohammed","ismaail",EmployeePosition.worker);
		Employee emp2 = new Employee(null,"mohammed","gomaa","ali",EmployeePosition.worker);
		Employee emp3 = new Employee(null,"mahmoud","gamal","mohammed",EmployeePosition.worker);
		
		employeeRepo.save(emp1);
		employeeRepo.save(emp2);
		employeeRepo.save(emp3);
		
		KilensShiftStatus kilens1 = new KilensShiftStatus();
		kilens1.setKiln1(List.of(pd1));
		kilens1.setKiln2(List.of(pd1));
		kilens1.setKiln3(List.of(pd1));
		kilens1.setKiln4(List.of(pd1));
		kilens1.setKiln5(List.of(pd1));
		
		KilensShiftStatus kilens2 = new KilensShiftStatus();
		kilens2.setKiln1(List.of(pd2));
		kilens2.setKiln2(List.of(pd2));
		kilens2.setKiln3(List.of(pd2));
		kilens2.setKiln4(List.of(pd2));
		kilens2.setKiln5(List.of(pd2));
		
		KilensShiftStatus kilens3 = new KilensShiftStatus();
		kilens3.setKiln1(List.of(pd1,pd2,pd3));
		kilens3.setKiln2(List.of(pd2,pd3));
		kilens3.setKiln3(List.of(pd1,pd3));
		kilens3.setKiln4(List.of(pd1,pd2));
		kilens3.setKiln5(List.of(pd1));
		
		kilensShiftStatusRepo.save(kilens1);
		kilensShiftStatusRepo.save(kilens2);
		kilensShiftStatusRepo.save(kilens3);
		
		DrayersShiftStatus drayers1 = new DrayersShiftStatus();
		drayers1.setDrayer1(List.of(pd2));
		drayers1.setDrayer2(List.of(pd2));
		drayers1.setDrayer3(List.of(pd2));
		drayers1.setDrayer4(List.of(pd2));
		drayers1.setDrayer5(List.of(pd2));
		drayers1.setDrayer6(List.of(pd2));
		drayers1.setDrayer7(List.of(pd2));
		
		DrayersShiftStatus drayers2 = new DrayersShiftStatus();
		drayers2.setDrayer1(List.of(pd3));
		drayers2.setDrayer2(List.of(pd3));
		drayers2.setDrayer3(List.of(pd3));
		drayers2.setDrayer4(List.of(pd3));
		drayers2.setDrayer5(List.of(pd3));
		drayers2.setDrayer6(List.of(pd3));
		drayers2.setDrayer7(List.of(pd3));
		
		DrayersShiftStatus drayers3 = new DrayersShiftStatus();
		drayers3.setDrayer1(List.of(pd1,pd2,pd3));
		drayers3.setDrayer2(List.of(pd2,pd3));
		drayers3.setDrayer3(List.of(pd1,pd3));
		drayers3.setDrayer4(List.of(pd1,pd2));
		drayers3.setDrayer5(List.of(pd3));
		drayers3.setDrayer6(List.of(pd1));
		drayers3.setDrayer7(List.of(pd2));
		
		drayersShiftStatusRepo.save(drayers1);
		drayersShiftStatusRepo.save(drayers2);
		drayersShiftStatusRepo.save(drayers3);
		
		AtmsShiftStatus atms1 = new AtmsShiftStatus();
		atms1.setAtm1(List.of(pd3));
		atms1.setAtm1(List.of(pd3));
		
		AtmsShiftStatus atms2 = new AtmsShiftStatus();
		atms2.setAtm1(List.of(pd2));
		atms2.setAtm1(List.of(pd3));
		
		AtmsShiftStatus atms3 = new AtmsShiftStatus();
		atms3.setAtm1(List.of(pd2,pd3));
		atms3.setAtm1(List.of(pd1));
		
		atmsShiftStatusRepo.save(atms1);
		atmsShiftStatusRepo.save(atms2);
		atmsShiftStatusRepo.save(atms3);
		
		Shift shift1 = new Shift();
		shift1.setKilens(kilens1);
		shift1.setDrayers(drayers1);
		shift1.setAtms(atms1);
		shift1.setEmployees(List.of(emp1));
		shift1.setProject(List.of(pd4));
		shift1.setMinTemperature(170);
		shift1.setMaxTemperature(210);
		
		Shift shift2 = new Shift();
		shift2.setKilens(kilens3);
		shift2.setDrayers(drayers3);
		shift2.setAtms(atms3);
		shift2.setEmployees(List.of(emp2));
		shift2.setProject(List.of(pd4));
		shift2.setMinTemperature(179);
		shift2.setMaxTemperature(204);
		
		Shift shift3 = new Shift();
		shift3.setKilens(kilens3);
		shift3.setDrayers(drayers3);
		shift3.setAtms(atms3);
		shift3.setEmployees(List.of(emp3));
		shift3.setProject(List.of(pd4));
		shift3.setMinTemperature(190);
		shift3.setMaxTemperature(200);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		WorkDay workDay = new WorkDay();
		workDay.setShift1(shift1);
		workDay.setShift2(shift2);
		workDay.setShift3(shift3);
		workDay.setDate(new Date());
		
		workdayRepo.save(workDay);
	}

	public List<WorkDay> getAllDays() {
		return workdayRepo.findAll();
	}
	
	public void exportAllDaysToLocalFile() {
		try {
			File file = new File(System
					.getProperty("user.home")+ File.separator+"rhrData.json");
			FileWriter fw = new FileWriter(file);
			new GsonBuilder().create().toJson(workdayRepo.findAll(),fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}




