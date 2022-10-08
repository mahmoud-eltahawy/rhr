package com.rhr.heat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.ProblemDetailsRepo;
import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.dao.TotalFlowRepo;
import com.rhr.heat.enums.AtmsCase;
import com.rhr.heat.enums.EmployeePosition;
import com.rhr.heat.enums.Machine;
import com.rhr.heat.enums.Problem;
import com.rhr.heat.enums.ShiftType;
import com.rhr.heat.model.Employee;
import com.rhr.heat.model.ProblemDetail;
import com.rhr.heat.model.Shift;
import com.rhr.heat.model.TotalFlow;
import com.rhr.heat.model.plate.MyDate;
import com.rhr.heat.model.plate.MyTime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Tester {
	private final EmployeeRepo employeeRepo;
	private final ProblemDetailsRepo problemDetailsRepo;
	private final TotalFlowRepo totalFlowRepo;
	private final ShiftRepo shiftRepo;
	
	public void insertData() {
		Employee emp1 = new Employee(null,"mahmoud","sabry","mohammed",EmployeePosition.Engineer);
		Employee emp2 = new Employee(null,"mahmoud","gamal","mohammed",EmployeePosition.worker);
		Employee emp3 = new Employee(null,"taha","mohammed","ismaail",EmployeePosition.worker);
		Employee emp4 = new Employee(null,"mohammed","gomaa","mohammed",EmployeePosition.worker);
		Employee emp5 = new Employee(null,"ehab","hagag","saad",EmployeePosition.worker);
		
		employeeRepo.save(emp1);
		employeeRepo.save(emp2);
		employeeRepo.save(emp3);
		employeeRepo.save(emp4);
		employeeRepo.save(emp5);
		
		ProblemDetail pd1  = new ProblemDetail(null, Problem.p1,new MyTime(0, 30),new MyTime(0, 30),Machine.Atm1);
		ProblemDetail pd2  = new ProblemDetail(null, Problem.p2,new MyTime(0, 30),new MyTime(0, 30),Machine.Atm2);
		ProblemDetail pd3  = new ProblemDetail(null, Problem.p3,new MyTime(0, 30),new MyTime(0, 30),Machine.Drayer1);
		ProblemDetail pd4  = new ProblemDetail(null, Problem.p4,new MyTime(0, 30),new MyTime(0, 30),Machine.Drayer2);
		ProblemDetail pd5  = new ProblemDetail(null, Problem.p5,new MyTime(0, 30),new MyTime(0, 30),Machine.Drayer3);
		ProblemDetail pd6  = new ProblemDetail(null, Problem.p6,new MyTime(0, 30),new MyTime(0, 30),Machine.Drayer4);
		ProblemDetail pd7  = new ProblemDetail(null, Problem.p7,new MyTime(0, 30),new MyTime(0, 30),Machine.Drayer5);
		ProblemDetail pd8  = new ProblemDetail(null, Problem.p8,new MyTime(0, 30),new MyTime(0, 30),Machine.Drayer6);
		ProblemDetail pd9  = new ProblemDetail(null, Problem.p9,new MyTime(0, 30),new MyTime(0, 30),Machine.Drayer7);
		ProblemDetail pd10 = new ProblemDetail(null, Problem.p1,new MyTime(0, 30),new MyTime(0, 30),Machine.Kilen1);
		ProblemDetail pd11 = new ProblemDetail(null, Problem.p1,new MyTime(0, 30),new MyTime(0, 30),Machine.Kilen2);
		ProblemDetail pd12 = new ProblemDetail(null, Problem.p2,new MyTime(0, 30),new MyTime(0, 30),Machine.Kilen3);
		ProblemDetail pd13 = new ProblemDetail(null, Problem.p3,new MyTime(0, 30),new MyTime(0, 30),Machine.Kilen4);
		ProblemDetail pd14 = new ProblemDetail(null, Problem.p4,new MyTime(0, 30),new MyTime(0, 30),Machine.Kilen5);
		
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
		
		TotalFlow tf1 = new TotalFlow(null,AtmsCase.ATM1_AND_ATM2,77,92,new MyTime(2,15),new MyTime(4,25));
		TotalFlow tf2 = new TotalFlow(null,AtmsCase.ATM_ONE_ONLY,77,92,new MyTime(2,15),new MyTime(4,35));
		TotalFlow tf3 = new TotalFlow(null,AtmsCase.ATM_Two_ONLY,77,92,new MyTime(3,15),new MyTime(5,25));
		TotalFlow tf4 = new TotalFlow(null,AtmsCase.NONE,77,92,new MyTime(2,15),new MyTime(4,25));
		TotalFlow tf5 = new TotalFlow(null,AtmsCase.ATM1_AND_ATM2,77,92,new MyTime(2,15),new MyTime(4,25));
		TotalFlow tf6 = new TotalFlow(null,AtmsCase.ATM_ONE_ONLY,77,92,new MyTime(3,15),new MyTime(4,25));
		TotalFlow tf7 = new TotalFlow(null,AtmsCase.ATM_Two_ONLY,77,92,new MyTime(2,15),new MyTime(4,25));
		
		totalFlowRepo.save(tf1);
		totalFlowRepo.save(tf2);
		totalFlowRepo.save(tf3);
		totalFlowRepo.save(tf4);
		totalFlowRepo.save(tf5);
		totalFlowRepo.save(tf6);
		totalFlowRepo.save(tf7);
		
		
		Shift shift1 = new Shift(new MyDate(2202,10,5),ShiftType.First,
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				"taha greeting", 175, 195);
		
		Shift shift2 = new Shift(new MyDate(2202,10,5),ShiftType.First,
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 175, 195);
		
		Shift shift3 = new Shift(new MyDate(2202,10,5),ShiftType.First,
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				"mahmoud greeting", 175, 195);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		shift1 = new Shift(new MyDate(2202,10,6),ShiftType.First,
				List.of(pd2,pd3),
				List.of(emp3,emp5),
				List.of(tf4,tf3),
				"taha and ehab greeting", 185, 205);
		
		shift2 = new Shift(new MyDate(2202,10,6),ShiftType.First,
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 195, 205);
		
		shift3 = new Shift(new MyDate(2202,10,6),ShiftType.First,
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 177, 197);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		shift1 = new Shift(new MyDate(2202,10,7),ShiftType.First,
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);
		
		shift2 = new Shift(new MyDate(2202,10,7),ShiftType.First,
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);
		
		shift3 = new Shift(new MyDate(2202,10,7),ShiftType.First,
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		shift1 = new Shift(new MyDate(2202,10,8),ShiftType.First,
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		shift2 = new Shift(new MyDate(2202,10,8),ShiftType.First,
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				"ehab greeting", 192, 202);
		
		shift3 = new Shift(new MyDate(2202,10,8),ShiftType.First,
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		shift1 = new Shift(new MyDate(2202,10,9),ShiftType.First,
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		shift2 = new Shift(new MyDate(2202,10,9),ShiftType.First,
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				"ehab and mohammed greeting", 192, 202);
		
		shift3 = new Shift(new MyDate(2202,10,9),ShiftType.First,
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		shift1 = new Shift(new MyDate(2202,10,10),ShiftType.First,
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		shift2 = new Shift(new MyDate(2202,10,10),ShiftType.First,
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 192, 202);
		
		shift3 = new Shift(new MyDate(2202,10,10),ShiftType.First,
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				"ehab greeting", 173, 195);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
		
		shift1 = new Shift(new MyDate(2202,10,11),ShiftType.First,
				List.of(pd12,pd11),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		shift2 = new Shift(new MyDate(2202,10,11),ShiftType.First,
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				"mohammed greeting", 192, 202);
		
		shift3 = new Shift(new MyDate(2202,10,11),ShiftType.First,
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				"mahmoud and ehab greeting", 173, 195);
		
		shiftRepo.save(shift1);
		shiftRepo.save(shift2);
		shiftRepo.save(shift3);
	}

	public List<Shift> getAllData(){
		return shiftRepo.findAll();
	}
	
	public void emp() {
		System.out.println(
		employeeRepo.deleteById(1L));
	}
}