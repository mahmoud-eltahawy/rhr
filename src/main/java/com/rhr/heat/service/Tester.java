package com.rhr.heat.service;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.enums.ConsumersCase;
import com.rhr.heat.enums.EmployeePosition;
import com.rhr.heat.enums.Machine;
import com.rhr.heat.enums.Problem;
import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.model.Employee;
import com.rhr.heat.model.ProblemDetail;
import com.rhr.heat.model.Shift;
import com.rhr.heat.model.ShiftId;
import com.rhr.heat.model.TotalFlow;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Tester {
	private final EmployeeRepo employeeRepo;
	private final ShiftRepo shiftRepo;
	
	public void insertData() {
		Employee emp1 = new Employee(null,"mahmoud","sabry","mohammed",EmployeePosition.Engineer);
		Employee emp2 = new Employee(null,"mahmoud","gamal","mohammed",EmployeePosition.worker);
		Employee emp3 = new Employee(null,"taha","mohammed","ismaail",EmployeePosition.worker);
		Employee emp4 = new Employee(null,"mohammed","gomaa","mohammed",EmployeePosition.worker);
		Employee emp5 = new Employee(null,"ehab","hagag","saad",EmployeePosition.worker);
		
		emp1.setId(employeeRepo.save(emp1));
		emp2.setId(employeeRepo.save(emp2));
		emp3.setId(employeeRepo.save(emp3));
		emp4.setId(employeeRepo.save(emp4));
		emp5.setId(employeeRepo.save(emp5));
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 45);
		cal.set(Calendar.AM_PM, Calendar.AM);
		Time begin = new Time(cal.getTime().getTime());
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 40);
		Time end = new Time(cal.getTime().getTime());
		
		ProblemDetail pd1  = new ProblemDetail(null, Problem.p1,Machine.Atm1,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 50);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd2  = new ProblemDetail(null, Problem.p2,Machine.Atm2,begin,end);
		cal.set(Calendar.HOUR, 12);
		cal.set(Calendar.MINUTE, 50);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd3  = new ProblemDetail(null, Problem.p3,Machine.Drayer1,begin,end);
		cal.set(Calendar.HOUR, 6);
		cal.set(Calendar.MINUTE, 6);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd4  = new ProblemDetail(null, Problem.p4,Machine.Drayer2,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 5);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd5  = new ProblemDetail(null, Problem.p5,Machine.Drayer3,begin,end);
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 30);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd6  = new ProblemDetail(null, Problem.p6,Machine.Drayer4,begin,end);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 5);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd7  = new ProblemDetail(null, Problem.p7,Machine.Drayer5,begin,end);
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 40);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd8  = new ProblemDetail(null, Problem.p8,Machine.Drayer6,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 30);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd9  = new ProblemDetail(null, Problem.p9,Machine.Drayer7,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 10);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd10 = new ProblemDetail(null, Problem.p1,Machine.Kilen1,begin,end);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 30);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd11 = new ProblemDetail(null, Problem.p1,Machine.Kilen2,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 20);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd12 = new ProblemDetail(null, Problem.p2,Machine.Kilen3,begin,end);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 45);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd13 = new ProblemDetail(null, Problem.p3,Machine.Kilen4,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd14 = new ProblemDetail(null, Problem.p4,Machine.Kilen5,begin,end);
		
		
		TotalFlow tf1 = new TotalFlow(null,ConsumersCase.ATM1_AND_ATM2,77,92,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		TotalFlow tf2 = new TotalFlow(null,ConsumersCase.ATM_ONE_ONLY,77,92,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 35);
		begin = new Time(cal.getTime().getTime());
		TotalFlow tf3 = new TotalFlow(null,ConsumersCase.ATM_Two_ONLY,77,92,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 15);
		end = new Time(cal.getTime().getTime());
		TotalFlow tf4 = new TotalFlow(null,ConsumersCase.NONE,77,92,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 15);
		begin = new Time(cal.getTime().getTime());
		TotalFlow tf5 = new TotalFlow(null,ConsumersCase.ATM1_AND_ATM2,77,92,begin,end);
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 10);
		end = new Time(cal.getTime().getTime());
		TotalFlow tf6 = new TotalFlow(null,ConsumersCase.ATM_ONE_ONLY,77,92,begin,end);
		cal.set(Calendar.HOUR, 12);
		cal.set(Calendar.MINUTE, 15);
		begin = new Time(cal.getTime().getTime());
		TotalFlow tf7 = new TotalFlow(null,ConsumersCase.ATM_Two_ONLY,77,92,begin,end);
		
		
		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2022);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DAY_OF_MONTH, 5);
		Date d = new Date(cal.getTime().getTime());
		
		Shift shift11 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				"taha greeting", 175, 195);
		
		Shift shift12 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd6,pd4),
				List.of(emp4),
				List.of(tf6,tf2),
				"mohammed greeting", 175, 195);
		
		Shift shift13 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				"mahmoud greeting", 175, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 6);
		d = new Date(cal.getTime().getTime());
		
		Shift shift21 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp3,emp5),
				List.of(tf4,tf3),
				"taha and ehab greeting", 185, 205);
		
		Shift shift22 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 195, 205);
		
		Shift shift23 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 177, 197);
		
		cal.set(Calendar.DAY_OF_MONTH, 7);
		d = new Date(cal.getTime().getTime());
		
		Shift shift31 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);
		
		Shift shift32 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);
		
		Shift shift33 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);
		
		cal.set(Calendar.DAY_OF_MONTH, 8);
		d = new Date(cal.getTime().getTime());
		
		Shift shift41 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift42 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				"ehab greeting", 192, 202);
		
		Shift shift43 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 9);
		d = new Date(cal.getTime().getTime());
		
		Shift shift51 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift52 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				"ehab and mohammed greeting", 192, 202);
		
		Shift shift53 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 10);
		d = new Date(cal.getTime().getTime());
		
		Shift shift61 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift62 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 192, 202);
		
		Shift shift63 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				"ehab greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 11);
		d = new Date(cal.getTime().getTime());
		
		Shift shift71 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd12,pd11),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift72 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				"mohammed greeting", 192, 202);
		
		Shift shift73 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				"mahmoud and ehab greeting", 173, 195);
		
		shiftRepo.saveAll(List.of(shift11,shift12,shift13,
				shift21,shift22,shift23,
				shift31,shift32,shift33,
				shift41,shift42,shift43,
				shift51,shift52,shift53,
				shift61,shift62,shift63,
				shift71,shift72,shift73));
	}

	public List<Shift> getAllData(){
		return shiftRepo.findAll();
	}
	
	public void emp() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2022);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DAY_OF_MONTH, 8);
		Date date = new Date(cal.getTime().getTime());
		shiftRepo.findOlderThan(date, ShiftOrder.FIRST)
		.forEach(s -> System.out.println( "from olderThan 8" + s.toString()));
		shiftRepo.findRecent(date, ShiftOrder.SECOND)
		.forEach(s -> System.out.println( "from recent 8" + s.toString()));
		cal.set(Calendar.DAY_OF_MONTH, 7);
		Date date1 = new Date(cal.getTime().getTime());
		cal.set(Calendar.DAY_OF_MONTH, 10);
		Date date2 = new Date(cal.getTime().getTime());
		shiftRepo.findBetween(date1,date2,ShiftOrder.THIRD).forEach(s -> System.out.println( "from between 7 10" + s.toString()));
		shiftRepo.findAll(date1).forEach(s -> System.out.println( "from All" + s.toString()));
		System.out.println( "from byId " + shiftRepo.findById(date2,ShiftOrder.SECOND).toString());
	}
}