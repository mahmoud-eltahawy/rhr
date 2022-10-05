package com.rhr.heat.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rhr.heat.dao.AtmsShiftStatusRepo;
import com.rhr.heat.dao.DrayersShiftStatusRepo;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.KilensShiftStatusRepo;
import com.rhr.heat.dao.ProblemDetailsRepo;
import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.dao.TotalFlowRepo;
import com.rhr.heat.dao.WorkdayRepo;
import com.rhr.heat.model.AtmsCase;
import com.rhr.heat.model.AtmsShiftStatus;
import com.rhr.heat.model.DrayersShiftStatus;
import com.rhr.heat.model.Employee;
import com.rhr.heat.model.EmployeePosition;
import com.rhr.heat.model.KilensShiftStatus;
import com.rhr.heat.model.Problem;
import com.rhr.heat.model.ProblemDetail;
import com.rhr.heat.model.Shift;
import com.rhr.heat.model.TotalFlow;
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
	private final TotalFlowRepo totalFlowRepo;
	
	public void insertWeek() {

		ProblemDetail pd1 = new ProblemDetail(null, Problem.p1, 2.5);
		ProblemDetail pd2 = new ProblemDetail(null, Problem.p2, 1.5);
		ProblemDetail pd3 = new ProblemDetail(null, Problem.p3, 0.5);
		ProblemDetail pd4 = new ProblemDetail(null, Problem.p4, 2.0);
		ProblemDetail pd5 = new ProblemDetail(null, Problem.p5, 4.0);
		ProblemDetail pd6 = new ProblemDetail(null, Problem.p6, 5.0);
		ProblemDetail pd7 = new ProblemDetail(null, Problem.p7, 3.0);
		ProblemDetail pd8 = new ProblemDetail(null, Problem.p8, 3.5);
		ProblemDetail pd9 = new ProblemDetail(null, Problem.p9, 4.5);
		ProblemDetail pd10 = new ProblemDetail(null, Problem.p1, 0.3);
		ProblemDetail pd11 = new ProblemDetail(null, Problem.p1, 1.4);
		ProblemDetail pd12 = new ProblemDetail(null, Problem.p2, 2.1);
		ProblemDetail pd13 = new ProblemDetail(null, Problem.p3, 1.9);
		ProblemDetail pd14 = new ProblemDetail(null, Problem.p4, 2.0);
		ProblemDetail pd15 = new ProblemDetail(null, Problem.p5, 3.2);
		ProblemDetail pd16 = new ProblemDetail(null, Problem.p6, 2.2);
		ProblemDetail pd17 = new ProblemDetail(null, Problem.p7, 2.7);
		ProblemDetail pd18 = new ProblemDetail(null, Problem.p8, 4.8);
		ProblemDetail pd19 = new ProblemDetail(null, Problem.p9, 5.1);
		
		problemDetailsRepo.save(pd1);
		problemDetailsRepo.save(pd2);
		problemDetailsRepo.save(pd3);
		problemDetailsRepo.save(pd4);
		problemDetailsRepo.save(pd5);
		problemDetailsRepo.save(pd6);
		problemDetailsRepo.save(pd7);
		problemDetailsRepo.save(pd8);
		problemDetailsRepo.save(pd9);
		problemDetailsRepo.save(pd10);
		problemDetailsRepo.save(pd11);
		problemDetailsRepo.save(pd12);
		problemDetailsRepo.save(pd13);
		problemDetailsRepo.save(pd14);
		problemDetailsRepo.save(pd15);
		problemDetailsRepo.save(pd16);
		problemDetailsRepo.save(pd17);
		problemDetailsRepo.save(pd18);
		problemDetailsRepo.save(pd19);
		
		TotalFlow tf1 = new TotalFlow(null,AtmsCase.ATM1_AND_ATM2,75,95,8.0);
		TotalFlow tf2 = new TotalFlow(null,AtmsCase.ATM_ONE_ONLY,40,49,8.0);
		TotalFlow tf3 = new TotalFlow(null,AtmsCase.ATM_Two_ONLY,45,55,8.0);
		TotalFlow tf4 = new TotalFlow(null,AtmsCase.NONE,11,22,8.0);
		TotalFlow tf5 = new TotalFlow(null,AtmsCase.ATM1_AND_ATM2,75,95,4.0);
		TotalFlow tf6 = new TotalFlow(null,AtmsCase.ATM_ONE_ONLY,40,49,4.0);
		TotalFlow tf7 = new TotalFlow(null,AtmsCase.ATM_Two_ONLY,45,55,4.0);
		TotalFlow tf8 = new TotalFlow(null,AtmsCase.NONE,11,22,4.0);
		TotalFlow tf9 = new TotalFlow(null,AtmsCase.ATM1_AND_ATM2,75,95,2.0);
		TotalFlow tf10 = new TotalFlow(null,AtmsCase.ATM_ONE_ONLY,40,49,2.0);
		TotalFlow tf11 = new TotalFlow(null,AtmsCase.ATM_Two_ONLY,45,55,2.0);
		TotalFlow tf12 = new TotalFlow(null,AtmsCase.NONE,11,22,2.0);
		
		totalFlowRepo.save(tf1);
		totalFlowRepo.save(tf2);
		totalFlowRepo.save(tf3);
		totalFlowRepo.save(tf4);
		totalFlowRepo.save(tf5);
		totalFlowRepo.save(tf6);
		totalFlowRepo.save(tf7);
		totalFlowRepo.save(tf8);
		totalFlowRepo.save(tf9);
		totalFlowRepo.save(tf10);
		totalFlowRepo.save(tf11);
		totalFlowRepo.save(tf12);
		
		Employee emp1 = new Employee(null,"mahmoud","sabry","mohammed",EmployeePosition.Engineer);
		Employee emp2 = new Employee(null,"mahmoud","gamal","mohammed",EmployeePosition.worker);
		Employee emp3 = new Employee(null,"taha","mohammed","ismaail",EmployeePosition.worker);
		Employee emp4 = new Employee(null,"mohammed","gomaa","ali",EmployeePosition.worker);
		Employee emp5 = new Employee(null,"ehab","hagag","saad",EmployeePosition.worker);
		
		employeeRepo.save(emp1);
		employeeRepo.save(emp2);
		employeeRepo.save(emp3);
		employeeRepo.save(emp4);
		employeeRepo.save(emp5);
		
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
		shift1.setEmployees(List.of(emp3));
		shift1.setProject(List.of(pd4));
		shift1.setTotalFlowAverage(List.of(tf1));
		shift1.setMinTemperature(170);
		shift1.setMaxTemperature(210);
		
		Shift shift2 = new Shift();
		shift2.setKilens(kilens3);
		shift2.setDrayers(drayers3);
		shift2.setAtms(atms3);
		shift2.setEmployees(List.of(emp4));
		shift2.setProject(List.of(pd4));
		shift2.setTotalFlowAverage(List.of(tf2));
		shift2.setMinTemperature(179);
		shift2.setMaxTemperature(204);
		
		Shift shift3 = new Shift();
		shift3.setKilens(kilens3);
		shift3.setDrayers(drayers3);
		shift3.setAtms(atms3);
		shift3.setEmployees(List.of(emp2));
		shift3.setProject(List.of(pd4));
		shift3.setTotalFlowAverage(List.of(tf3));
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
		
		kilens1 = new KilensShiftStatus();
		kilens1.setKiln1(List.of(pd4));
		kilens1.setKiln2(List.of(pd4));
		kilens1.setKiln3(List.of(pd4));
		kilens1.setKiln4(List.of(pd4));
		kilens1.setKiln5(List.of(pd4));
		
		kilens2 = new KilensShiftStatus();
		kilens2.setKiln1(List.of(pd5));
		kilens2.setKiln2(List.of(pd5));
		kilens2.setKiln3(List.of(pd5));
		kilens2.setKiln4(List.of(pd5));
		kilens2.setKiln5(List.of(pd5));
		
		kilens3 = new KilensShiftStatus();
		kilens3.setKiln1(List.of(pd4,pd5,pd6));
		kilens3.setKiln2(List.of(pd5,pd6));
		kilens3.setKiln3(List.of(pd1,pd6));
		kilens3.setKiln4(List.of(pd8,pd9));
		kilens3.setKiln5(List.of(pd9));
		
		kilensShiftStatusRepo.save(kilens1);
		kilensShiftStatusRepo.save(kilens2);
		kilensShiftStatusRepo.save(kilens3);
		
		drayers1 = new DrayersShiftStatus();
		drayers1.setDrayer1(List.of(pd11));
		drayers1.setDrayer2(List.of(pd12));
		drayers1.setDrayer3(List.of(pd12));
		drayers1.setDrayer4(List.of(pd12));
		drayers1.setDrayer5(List.of(pd12));
		drayers1.setDrayer6(List.of(pd12));
		drayers1.setDrayer7(List.of(pd12));
		
		drayers2 = new DrayersShiftStatus();
		drayers2.setDrayer1(List.of(pd13));
		drayers2.setDrayer2(List.of(pd13));
		drayers2.setDrayer3(List.of(pd13));
		drayers2.setDrayer4(List.of(pd13));
		drayers2.setDrayer5(List.of(pd13));
		drayers2.setDrayer6(List.of(pd13));
		drayers2.setDrayer7(List.of(pd13));
		
		drayers3 = new DrayersShiftStatus();
		drayers3.setDrayer1(List.of(pd11,pd12,pd13));
		drayers3.setDrayer2(List.of(pd12,pd13));
		drayers3.setDrayer3(List.of(pd11,pd13));
		drayers3.setDrayer4(List.of(pd11,pd12));
		drayers3.setDrayer5(List.of(pd13));
		drayers3.setDrayer6(List.of(pd11));
		drayers3.setDrayer7(List.of(pd12));
		
		drayersShiftStatusRepo.save(drayers1);
		drayersShiftStatusRepo.save(drayers2);
		drayersShiftStatusRepo.save(drayers3);
		
		atms1 = new AtmsShiftStatus();
		atms1.setAtm1(List.of(pd14));
		atms1.setAtm1(List.of(pd14));
		
		atms2 = new AtmsShiftStatus();
		atms2.setAtm1(List.of(pd15));
		atms2.setAtm1(List.of(pd16));
		
		atms3 = new AtmsShiftStatus();
		atms3.setAtm1(List.of(pd17,pd18));
		atms3.setAtm1(List.of(pd19));
		
		atmsShiftStatusRepo.save(atms1);
		atmsShiftStatusRepo.save(atms2);
		atmsShiftStatusRepo.save(atms3);
		
		shift1 = new Shift();
		shift1.setKilens(kilens1);
		shift1.setDrayers(drayers1);
		shift1.setAtms(atms1);
		shift1.setEmployees(List.of(emp3,emp5));
		shift1.setProject(List.of(pd4));
		shift1.setTotalFlowAverage(List.of(tf4));
		shift1.setMinTemperature(175);
		shift1.setMaxTemperature(202);
		
		shift2 = new Shift();
		shift2.setKilens(kilens3);
		shift2.setDrayers(drayers3);
		shift2.setAtms(atms3);
		shift2.setEmployees(List.of(emp4));
		shift2.setProject(List.of(pd4));
		shift2.setTotalFlowAverage(List.of(tf5,tf6));
		shift2.setMinTemperature(182);
		shift2.setMaxTemperature(201);
		
		shift3 = new Shift();
		shift3.setKilens(kilens3);
		shift3.setDrayers(drayers3);
		shift3.setAtms(atms3);
		shift3.setEmployees(List.of(emp2));
		shift3.setProject(List.of(pd4));
		shift3.setTotalFlowAverage(List.of(tf5,tf7));
		shift3.setMinTemperature(191);
		shift3.setMaxTemperature(200);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		workDay = new WorkDay();
		workDay.setShift1(shift1);
		workDay.setShift2(shift2);
		workDay.setShift3(shift3);
		workDay.setDate(new Date());
		
		workdayRepo.save(workDay);
		
		kilens1 = new KilensShiftStatus();
		kilens1.setKiln1(List.of(pd14));
		kilens1.setKiln2(List.of(pd14));
		kilens1.setKiln3(List.of(pd14));
		kilens1.setKiln4(List.of(pd14));
		kilens1.setKiln5(List.of(pd14));
		
		kilens2 = new KilensShiftStatus();
		kilens2.setKiln1(List.of(pd15));
		kilens2.setKiln2(List.of(pd15));
		kilens2.setKiln3(List.of(pd15));
		kilens2.setKiln4(List.of(pd15));
		kilens2.setKiln5(List.of(pd15));
		
		kilens3 = new KilensShiftStatus();
		kilens3.setKiln1(List.of(pd15,pd5,pd16));
		kilens3.setKiln2(List.of(pd5,pd16));
		kilens3.setKiln3(List.of(pd1,pd16));
		kilens3.setKiln4(List.of(pd8,pd19));
		kilens3.setKiln5(List.of(pd19));
		
		kilensShiftStatusRepo.save(kilens1);
		kilensShiftStatusRepo.save(kilens2);
		kilensShiftStatusRepo.save(kilens3);
		
		drayers1 = new DrayersShiftStatus();
		drayers1.setDrayer1(List.of(pd18));
		drayers1.setDrayer2(List.of(pd12));
		drayers1.setDrayer3(List.of(pd12));
		drayers1.setDrayer4(List.of(pd16));
		drayers1.setDrayer5(List.of(pd12));
		drayers1.setDrayer6(List.of(pd12));
		drayers1.setDrayer7(List.of(pd16));
		
		drayers2 = new DrayersShiftStatus();
		drayers2.setDrayer1(List.of(pd17));
		drayers2.setDrayer2(List.of(pd13));
		drayers2.setDrayer3(List.of(pd13));
		drayers2.setDrayer4(List.of(pd17));
		drayers2.setDrayer5(List.of(pd13));
		drayers2.setDrayer6(List.of(pd17));
		drayers2.setDrayer7(List.of(pd13));
		
		drayers3 = new DrayersShiftStatus();
		drayers3.setDrayer1(List.of(pd17,pd12,pd13));
		drayers3.setDrayer2(List.of(pd12,pd13));
		drayers3.setDrayer3(List.of(pd11,pd13));
		drayers3.setDrayer4(List.of(pd17,pd12));
		drayers3.setDrayer5(List.of(pd13));
		drayers3.setDrayer6(List.of(pd17));
		drayers3.setDrayer7(List.of(pd12));
		
		drayersShiftStatusRepo.save(drayers1);
		drayersShiftStatusRepo.save(drayers2);
		drayersShiftStatusRepo.save(drayers3);
		
		atms1 = new AtmsShiftStatus();
		atms1.setAtm1(List.of(pd18));
		atms1.setAtm1(List.of(pd15));
		
		atms2 = new AtmsShiftStatus();
		atms2.setAtm1(List.of(pd12));
		atms2.setAtm1(List.of(pd13));
		
		atms3 = new AtmsShiftStatus();
		atms3.setAtm1(List.of(pd14,pd17));
		atms3.setAtm1(List.of(pd15));
		
		atmsShiftStatusRepo.save(atms1);
		atmsShiftStatusRepo.save(atms2);
		atmsShiftStatusRepo.save(atms3);
		
		shift1 = new Shift();
		shift1.setKilens(kilens1);
		shift1.setDrayers(drayers1);
		shift1.setAtms(atms1);
		shift1.setEmployees(List.of(emp5));
		shift1.setProject(List.of(pd4));
		shift1.setTotalFlowAverage(List.of(tf5,tf8));
		shift1.setMinTemperature(190);
		shift1.setMaxTemperature(205);
		
		shift2 = new Shift();
		shift2.setKilens(kilens3);
		shift2.setDrayers(drayers3);
		shift2.setAtms(atms3);
		shift2.setEmployees(List.of(emp4));
		shift2.setProject(List.of(pd4));
		shift2.setTotalFlowAverage(List.of(tf6,tf7));
		shift2.setMinTemperature(186);
		shift2.setMaxTemperature(207);
		
		shift3 = new Shift();
		shift3.setKilens(kilens3);
		shift3.setDrayers(drayers3);
		shift3.setAtms(atms3);
		shift3.setEmployees(List.of(emp2));
		shift3.setProject(List.of(pd4));
		shift3.setTotalFlowAverage(List.of(tf6,tf8));
		shift3.setMinTemperature(193);
		shift3.setMaxTemperature(202);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		workDay = new WorkDay();
		workDay.setShift1(shift1);
		workDay.setShift2(shift2);
		workDay.setShift3(shift3);
		workDay.setDate(new Date());
		
		workdayRepo.save(workDay);
		
		kilens1 = new KilensShiftStatus();
		kilens1.setKiln1(List.of(pd8));
		kilens1.setKiln2(List.of(pd4));
		kilens1.setKiln3(List.of(pd9));
		kilens1.setKiln4(List.of(pd4));
		kilens1.setKiln5(List.of(pd7));
		
		kilens2 = new KilensShiftStatus();
		kilens2.setKiln1(List.of(pd3));
		kilens2.setKiln2(List.of(pd5));
		kilens2.setKiln3(List.of(pd1));
		kilens2.setKiln4(List.of(pd2));
		kilens2.setKiln5(List.of(pd5));
		
		kilens3 = new KilensShiftStatus();
		kilens3.setKiln1(List.of(pd7,pd5,pd6));
		kilens3.setKiln2(List.of(pd5,pd6));
		kilens3.setKiln3(List.of(pd1,pd6));
		kilens3.setKiln4(List.of(pd6,pd9));
		kilens3.setKiln5(List.of(pd9));
		
		kilensShiftStatusRepo.save(kilens1);
		kilensShiftStatusRepo.save(kilens2);
		kilensShiftStatusRepo.save(kilens3);
		
		drayers1 = new DrayersShiftStatus();
		drayers1.setDrayer1(List.of(pd11));
		drayers1.setDrayer2(List.of(pd12));
		drayers1.setDrayer3(List.of(pd13));
		drayers1.setDrayer4(List.of(pd12));
		drayers1.setDrayer5(List.of(pd16));
		drayers1.setDrayer6(List.of(pd12));
		drayers1.setDrayer7(List.of(pd19));
		
		drayers2 = new DrayersShiftStatus();
		drayers2.setDrayer1(List.of(pd13));
		drayers2.setDrayer2(List.of(pd15));
		drayers2.setDrayer3(List.of(pd13));
		drayers2.setDrayer4(List.of(pd13));
		drayers2.setDrayer5(List.of(pd17));
		drayers2.setDrayer6(List.of(pd13));
		drayers2.setDrayer7(List.of(pd19));
		
		drayers3 = new DrayersShiftStatus();
		drayers3.setDrayer1(List.of(pd11,pd12,pd13));
		drayers3.setDrayer2(List.of(pd14,pd15));
		drayers3.setDrayer3(List.of(pd16,pd17));
		drayers3.setDrayer4(List.of(pd18,pd19));
		drayers3.setDrayer5(List.of(pd11));
		drayers3.setDrayer6(List.of(pd10));
		drayers3.setDrayer7(List.of(pd10));
		
		drayersShiftStatusRepo.save(drayers1);
		drayersShiftStatusRepo.save(drayers2);
		drayersShiftStatusRepo.save(drayers3);
		
		atms1 = new AtmsShiftStatus();
		atms1.setAtm1(List.of(pd15));
		atms1.setAtm1(List.of(pd16));
		
		atms2 = new AtmsShiftStatus();
		atms2.setAtm1(List.of(pd17));
		atms2.setAtm1(List.of(pd16));
		
		atms3 = new AtmsShiftStatus();
		atms3.setAtm1(List.of(pd13,pd18));
		atms3.setAtm1(List.of(pd19));
		
		atmsShiftStatusRepo.save(atms1);
		atmsShiftStatusRepo.save(atms2);
		atmsShiftStatusRepo.save(atms3);
		
		shift1 = new Shift();
		shift1.setKilens(kilens1);
		shift1.setDrayers(drayers1);
		shift1.setAtms(atms1);
		shift1.setEmployees(List.of(emp3));
		shift1.setProject(List.of(pd4));
		shift1.setTotalFlowAverage(List.of(tf7,tf8));
		shift1.setMinTemperature(177);
		shift1.setMaxTemperature(203);
		
		shift2 = new Shift();
		shift2.setKilens(kilens3);
		shift2.setDrayers(drayers3);
		shift2.setAtms(atms3);
		shift2.setEmployees(List.of(emp4));
		shift2.setProject(List.of(pd4));
		shift2.setTotalFlowAverage(List.of(tf5,tf9,tf10));
		shift2.setMinTemperature(188);
		shift2.setMaxTemperature(202);
		
		shift3 = new Shift();
		shift3.setKilens(kilens3);
		shift3.setDrayers(drayers3);
		shift3.setAtms(atms3);
		shift3.setEmployees(List.of(emp2));
		shift3.setProject(List.of(pd4));
		shift3.setTotalFlowAverage(List.of(tf5,tf9,tf11));
		shift3.setMinTemperature(191);
		shift3.setMaxTemperature(201);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		workDay = new WorkDay();
		workDay.setShift1(shift1);
		workDay.setShift2(shift2);
		workDay.setShift3(shift3);
		workDay.setDate(new Date());
		
		workdayRepo.save(workDay);
		
		kilens1 = new KilensShiftStatus();
		kilens1.setKiln1(List.of(pd18));
		kilens1.setKiln2(List.of(pd14));
		kilens1.setKiln3(List.of(pd14));
		kilens1.setKiln4(List.of(pd14));
		kilens1.setKiln5(List.of(pd15));
		
		kilens2 = new KilensShiftStatus();
		kilens2.setKiln1(List.of(pd15));
		kilens2.setKiln2(List.of(pd12));
		kilens2.setKiln3(List.of(pd15));
		kilens2.setKiln4(List.of(pd15));
		kilens2.setKiln5(List.of(pd14));
		
		kilens3 = new KilensShiftStatus();
		kilens3.setKiln1(List.of(pd17,pd8,pd16));
		kilens3.setKiln2(List.of(pd5,pd16));
		kilens3.setKiln3(List.of(pd1,pd16));
		kilens3.setKiln4(List.of(pd8,pd19));
		kilens3.setKiln5(List.of(pd19));
		
		kilensShiftStatusRepo.save(kilens1);
		kilensShiftStatusRepo.save(kilens2);
		kilensShiftStatusRepo.save(kilens3);
		
		drayers1 = new DrayersShiftStatus();
		drayers1.setDrayer1(List.of(pd18));
		drayers1.setDrayer2(List.of(pd13));
		drayers1.setDrayer3(List.of(pd12));
		drayers1.setDrayer4(List.of(pd16));
		drayers1.setDrayer5(List.of(pd17));
		drayers1.setDrayer6(List.of(pd12));
		drayers1.setDrayer7(List.of(pd18));
		
		drayers2 = new DrayersShiftStatus();
		drayers2.setDrayer1(List.of(pd17));
		drayers2.setDrayer2(List.of(pd13));
		drayers2.setDrayer3(List.of(pd13));
		drayers2.setDrayer4(List.of(pd17));
		drayers2.setDrayer5(List.of(pd13));
		drayers2.setDrayer6(List.of(pd17));
		drayers2.setDrayer7(List.of(pd13));
		
		drayers3 = new DrayersShiftStatus();
		drayers3.setDrayer1(List.of(pd17,pd13));
		drayers3.setDrayer2(List.of(pd12,pd13));
		drayers3.setDrayer3(List.of(pd17,pd13));
		drayers3.setDrayer4(List.of(pd17,pd12));
		drayers3.setDrayer5(List.of());
		drayers3.setDrayer6(List.of(pd17));
		drayers3.setDrayer7(List.of(pd12));
		
		drayersShiftStatusRepo.save(drayers1);
		drayersShiftStatusRepo.save(drayers2);
		drayersShiftStatusRepo.save(drayers3);
		
		atms1 = new AtmsShiftStatus();
		atms1.setAtm1(List.of(pd15));
		atms1.setAtm1(List.of(pd13));
		
		atms2 = new AtmsShiftStatus();
		atms2.setAtm1(List.of(pd13));
		atms2.setAtm1(List.of(pd15));
		
		atms3 = new AtmsShiftStatus();
		atms3.setAtm1(List.of(pd18,pd17));
		atms3.setAtm1(List.of(pd15));
		
		atmsShiftStatusRepo.save(atms1);
		atmsShiftStatusRepo.save(atms2);
		atmsShiftStatusRepo.save(atms3);
		
		shift1 = new Shift();
		shift1.setKilens(kilens1);
		shift1.setDrayers(drayers1);
		shift1.setAtms(atms1);
		shift1.setEmployees(List.of(emp3));
		shift1.setProject(List.of(pd4));
		shift1.setTotalFlowAverage(List.of(tf5,tf9,tf12));
		shift1.setMinTemperature(180);
		shift1.setMaxTemperature(215);
		
		shift2 = new Shift();
		shift2.setKilens(kilens3);
		shift2.setDrayers(drayers3);
		shift2.setAtms(atms3);
		shift2.setEmployees(List.of(emp4,emp5));
		shift2.setProject(List.of(pd4));
		shift2.setTotalFlowAverage(List.of(tf5,tf10,tf11));
		shift2.setMinTemperature(189);
		shift2.setMaxTemperature(202);
		
		shift3 = new Shift();
		shift3.setKilens(kilens3);
		shift3.setDrayers(drayers3);
		shift3.setAtms(atms3);
		shift3.setEmployees(List.of(emp2));
		shift3.setProject(List.of(pd4));
		shift3.setTotalFlowAverage(List.of(tf5,tf10,tf12));
		shift3.setMinTemperature(191);
		shift3.setMaxTemperature(201);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		workDay = new WorkDay();
		workDay.setShift1(shift1);
		workDay.setShift2(shift2);
		workDay.setShift3(shift3);
		workDay.setDate(new Date());
		
		workdayRepo.save(workDay);
		
		kilens1 = new KilensShiftStatus();
		kilens1.setKiln1(List.of());
		kilens1.setKiln2(List.of(pd4));
		kilens1.setKiln3(List.of());
		kilens1.setKiln4(List.of(pd4));
		kilens1.setKiln5(List.of());
		
		kilens2 = new KilensShiftStatus();
		kilens2.setKiln1(List.of(pd3));
		kilens2.setKiln2(List.of());
		kilens2.setKiln3(List.of(pd1));
		kilens2.setKiln4(List.of(pd2));
		kilens2.setKiln5(List.of());
		
		kilens3 = new KilensShiftStatus();
		kilens3.setKiln1(List.of(pd5,pd6));
		kilens3.setKiln2(List.of(pd5,pd6));
		kilens3.setKiln3(List.of(pd1,pd6));
		kilens3.setKiln4(List.of(pd9));
		kilens3.setKiln5(List.of());
		
		kilensShiftStatusRepo.save(kilens1);
		kilensShiftStatusRepo.save(kilens2);
		kilensShiftStatusRepo.save(kilens3);
		
		drayers1 = new DrayersShiftStatus();
		drayers1.setDrayer1(List.of());
		drayers1.setDrayer2(List.of(pd12));
		drayers1.setDrayer3(List.of(pd13));
		drayers1.setDrayer4(List.of(pd12));
		drayers1.setDrayer5(List.of(pd16));
		drayers1.setDrayer6(List.of());
		drayers1.setDrayer7(List.of(pd19));
		
		drayers2 = new DrayersShiftStatus();
		drayers2.setDrayer1(List.of(pd13));
		drayers2.setDrayer2(List.of());
		drayers2.setDrayer3(List.of(pd13));
		drayers2.setDrayer4(List.of(pd13));
		drayers2.setDrayer5(List.of());
		drayers2.setDrayer6(List.of(pd13));
		drayers2.setDrayer7(List.of());
		
		drayers3 = new DrayersShiftStatus();
		drayers3.setDrayer1(List.of(pd11,pd13));
		drayers3.setDrayer2(List.of(pd14,pd15));
		drayers3.setDrayer3(List.of(pd16,pd17));
		drayers3.setDrayer4(List.of(pd18));
		drayers3.setDrayer5(List.of(pd11));
		drayers3.setDrayer6(List.of(pd10));
		drayers3.setDrayer7(List.of());
		
		drayersShiftStatusRepo.save(drayers1);
		drayersShiftStatusRepo.save(drayers2);
		drayersShiftStatusRepo.save(drayers3);
		
		atms1 = new AtmsShiftStatus();
		atms1.setAtm1(List.of(pd15));
		atms1.setAtm1(List.of());
		
		atms2 = new AtmsShiftStatus();
		atms2.setAtm1(List.of());
		atms2.setAtm1(List.of(pd16));
		
		atms3 = new AtmsShiftStatus();
		atms3.setAtm1(List.of(pd18));
		atms3.setAtm1(List.of(pd19));
		
		atmsShiftStatusRepo.save(atms1);
		atmsShiftStatusRepo.save(atms2);
		atmsShiftStatusRepo.save(atms3);
		
		shift1 = new Shift();
		shift1.setKilens(kilens1);
		shift1.setDrayers(drayers1);
		shift1.setAtms(atms1);
		shift1.setEmployees(List.of(emp3));
		shift1.setProject(List.of(pd4));
		shift1.setTotalFlowAverage(List.of(tf5,tf11,tf12));
		shift1.setMinTemperature(178);
		shift1.setMaxTemperature(208);
		
		shift2 = new Shift();
		shift2.setKilens(kilens3);
		shift2.setDrayers(drayers3);
		shift2.setAtms(atms3);
		shift2.setEmployees(List.of(emp4));
		shift2.setProject(List.of(pd4));
		shift2.setTotalFlowAverage(List.of(tf6,tf11,tf12));
		shift2.setMinTemperature(189);
		shift2.setMaxTemperature(209);
		
		shift3 = new Shift();
		shift3.setKilens(kilens3);
		shift3.setDrayers(drayers3);
		shift3.setAtms(atms3);
		shift3.setEmployees(List.of(emp5));
		shift3.setProject(List.of(pd4));
		shift3.setTotalFlowAverage(List.of(tf7,tf11,tf12));
		shift3.setMinTemperature(194);
		shift3.setMaxTemperature(204);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		workDay = new WorkDay();
		workDay.setShift1(shift1);
		workDay.setShift2(shift2);
		workDay.setShift3(shift3);
		workDay.setDate(new Date());
		
		workdayRepo.save(workDay);
		
		kilens1 = new KilensShiftStatus();
		kilens1.setKiln1(List.of(pd18));
		kilens1.setKiln2(List.of());
		kilens1.setKiln3(List.of(pd14));
		kilens1.setKiln4(List.of(pd14));
		kilens1.setKiln5(List.of());
		
		kilens2 = new KilensShiftStatus();
		kilens2.setKiln1(List.of(pd15));
		kilens2.setKiln2(List.of());
		kilens2.setKiln3(List.of(pd15));
		kilens2.setKiln4(List.of());
		kilens2.setKiln5(List.of(pd14));
		
		kilens3 = new KilensShiftStatus();
		kilens3.setKiln1(List.of(pd17,pd8));
		kilens3.setKiln2(List.of(pd5,pd16));
		kilens3.setKiln3(List.of(pd1,pd16));
		kilens3.setKiln4(List.of(pd19));
		kilens3.setKiln5(List.of(pd19));
		
		kilensShiftStatusRepo.save(kilens1);
		kilensShiftStatusRepo.save(kilens2);
		kilensShiftStatusRepo.save(kilens3);
		
		drayers1 = new DrayersShiftStatus();
		drayers1.setDrayer1(List.of(pd18));
		drayers1.setDrayer2(List.of());
		drayers1.setDrayer3(List.of(pd12));
		drayers1.setDrayer4(List.of(pd16));
		drayers1.setDrayer5(List.of(pd17));
		drayers1.setDrayer6(List.of());
		drayers1.setDrayer7(List.of(pd18));
		
		drayers2 = new DrayersShiftStatus();
		drayers2.setDrayer1(List.of(pd17));
		drayers2.setDrayer2(List.of());
		drayers2.setDrayer3(List.of(pd13));
		drayers2.setDrayer4(List.of(pd17));
		drayers2.setDrayer5(List.of(pd13));
		drayers2.setDrayer6(List.of());
		drayers2.setDrayer7(List.of(pd13));
		
		drayers3 = new DrayersShiftStatus();
		drayers3.setDrayer1(List.of(pd17,pd13));
		drayers3.setDrayer2(List.of(pd12,pd13));
		drayers3.setDrayer3(List.of(pd13));
		drayers3.setDrayer4(List.of(pd17,pd12));
		drayers3.setDrayer5(List.of());
		drayers3.setDrayer6(List.of(pd17));
		drayers3.setDrayer7(List.of(pd12));
		
		drayersShiftStatusRepo.save(drayers1);
		drayersShiftStatusRepo.save(drayers2);
		drayersShiftStatusRepo.save(drayers3);
		
		atms1 = new AtmsShiftStatus();
		atms1.setAtm1(List.of());
		atms1.setAtm1(List.of());
		
		atms2 = new AtmsShiftStatus();
		atms2.setAtm1(List.of());
		atms2.setAtm1(List.of(pd15));
		
		atms3 = new AtmsShiftStatus();
		atms3.setAtm1(List.of(pd17));
		atms3.setAtm1(List.of(pd15));
		
		atmsShiftStatusRepo.save(atms1);
		atmsShiftStatusRepo.save(atms2);
		atmsShiftStatusRepo.save(atms3);
		
		shift1 = new Shift();
		shift1.setKilens(kilens1);
		shift1.setDrayers(drayers1);
		shift1.setAtms(atms1);
		shift1.setEmployees(List.of(emp3));
		shift1.setProject(List.of(pd4));
		shift1.setTotalFlowAverage(List.of(tf8,tf11,tf12));
		shift1.setMinTemperature(187);
		shift1.setMaxTemperature(217);
		
		shift2 = new Shift();
		shift2.setKilens(kilens3);
		shift2.setDrayers(drayers3);
		shift2.setAtms(atms3);
		shift2.setEmployees(List.of(emp4));
		shift2.setProject(List.of(pd4));
		shift2.setTotalFlowAverage(List.of(tf8,tf9,tf10));
		shift2.setMinTemperature(179);
		shift2.setMaxTemperature(222);
		
		shift3 = new Shift();
		shift3.setKilens(kilens3);
		shift3.setDrayers(drayers3);
		shift3.setAtms(atms3);
		shift3.setEmployees(List.of(emp2,emp5));
		shift3.setProject(List.of(pd4));
		shift3.setTotalFlowAverage(List.of(tf8,tf9,tf11));
		shift3.setMinTemperature(193);
		shift3.setMaxTemperature(203);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		workDay = new WorkDay();
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
			new Gson().toJson(getAllDays(),fw);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}




