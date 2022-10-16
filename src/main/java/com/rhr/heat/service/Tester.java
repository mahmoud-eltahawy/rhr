package com.rhr.heat.service;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.enums.EmployeePosition;
import com.rhr.heat.enums.Machine;
import com.rhr.heat.enums.Problem;
import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.model.Day;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Tester {
	private final EmployeeRepo employeeRepo;
	private final ShiftRepo shiftRepo;
	
	public void insertData() {
		Employee emp1 = new Employee(null,"mahmoud","sabry","mohammed",EmployeePosition.Engineer,"mahmoud_sabry","1234");
		Employee emp2 = new Employee(null,"mahmoud","gamal","mohammed",EmployeePosition.worker,"mahmoud_gamal","1234");
		Employee emp3 = new Employee(null,"taha","mohammed","ismaail",EmployeePosition.worker,"taha_mohammed","1234");
		Employee emp4 = new Employee(null,"mohammed","gomaa","mohammed",EmployeePosition.worker,"mohammed_gomaa","1234");
		Employee emp5 = new Employee(null,"ehab","hagag","saad",EmployeePosition.worker,"ehab_hagag","1234");
		
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
		
		ProblemDetail pd1  = new ProblemDetail(null,Set.of(Problem.P1,Problem.P2),Machine.ATM_ONE,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 50);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd2  = new ProblemDetail(null,Set.of(Problem.P3,Problem.P4),Machine.ATM_TWO,begin,end);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 50);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd3  = new ProblemDetail(null,Set.of(Problem.P5,Problem.P6),Machine.DRAYER_ONE,begin,end);
		cal.set(Calendar.HOUR, 6);
		cal.set(Calendar.MINUTE, 6);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd4  = new ProblemDetail(null,Set.of(Problem.P7,Problem.P8),Machine.DRAYER_TWO,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 5);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd5  = new ProblemDetail(null,Set.of(Problem.P9,Problem.P1),Machine.DRAYER_THREE,begin,end);
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 30);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd6  = new ProblemDetail(null,Set.of(Problem.P2,Problem.P5),Machine.DRAYER_FOUR,begin,end);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 5);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd7  = new ProblemDetail(null,Set.of(Problem.P4,Problem.P7),Machine.DRAYER_FIVE,begin,end);
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 40);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd8  = new ProblemDetail(null,Set.of(Problem.P8,Problem.P3),Machine.DRAYER_SIX,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 30);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd9  = new ProblemDetail(null,Set.of(Problem.P4,Problem.P6),Machine.DRAYER_SEVEN,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 10);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd10 = new ProblemDetail(null,Set.of(Problem.P5,Problem.P7),Machine.ATM_ONE,begin,end);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 30);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd11 = new ProblemDetail(null,Set.of(Problem.P1,Problem.P8),Machine.ATM_TWO,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 20);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd12 = new ProblemDetail(null,Set.of(Problem.P1,Problem.P8,Problem.P3),Machine.PROJECT,begin,end);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 45);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd13 = new ProblemDetail(null,Set.of(Problem.P2,Problem.P9,Problem.P4),Machine.PROJECT,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd14 = new ProblemDetail(null,Set.of(Problem.P5,Problem.P6,Problem.P7),Machine.KILEN_FIVE,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd15 = new ProblemDetail(null,Set.of(Problem.P5,Problem.P6,Problem.P7),Machine.KILEN_ONE,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd16 = new ProblemDetail(null,Set.of(Problem.P5,Problem.P6,Problem.P7),Machine.KILEN_ONE,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd17 = new ProblemDetail(null,Set.of(Problem.P5,Problem.P6,Problem.P7),Machine.KILEN_TWO,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd18 = new ProblemDetail(null,Set.of(Problem.P5,Problem.P6,Problem.P7),Machine.KILEN_THREE,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd19 = new ProblemDetail(null,Set.of(Problem.P5,Problem.P6,Problem.P7),Machine.KILEN_FOUR,begin,end);
		
		
		TotalFlow tf1 = new TotalFlow(null,List.of(Machine.ATM_ONE,Machine.DRAYER_TWO),77,92,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		TotalFlow tf2 = new TotalFlow(null,List.of(Machine.ATM_ONE,Machine.DRAYER_TWO),77,92,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 35);
		begin = new Time(cal.getTime().getTime());
		TotalFlow tf3 = new TotalFlow(null,List.of(Machine.ATM_ONE,Machine.DRAYER_TWO),77,92,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 15);
		end = new Time(cal.getTime().getTime());
		TotalFlow tf4 = new TotalFlow(null,List.of(Machine.ATM_ONE,Machine.DRAYER_TWO),77,92,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 15);
		begin = new Time(cal.getTime().getTime());
		TotalFlow tf5 = new TotalFlow(null,List.of(Machine.ATM_ONE,Machine.DRAYER_TWO),77,92,begin,end);
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 10);
		end = new Time(cal.getTime().getTime());
		TotalFlow tf6 = new TotalFlow(null,List.of(Machine.ATM_ONE,Machine.DRAYER_TWO),77,92,begin,end);
		cal.set(Calendar.HOUR, 00);
		cal.set(Calendar.MINUTE, 15);
		begin = new Time(cal.getTime().getTime());
		TotalFlow tf7 = new TotalFlow(null,List.of(Machine.ATM_ONE,Machine.DRAYER_TWO),77,92,begin,end);
		
		
		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2022);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DAY_OF_MONTH, 5);
		Date d = new Date(cal.getTime().getTime());
		
		Shift shift111 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				"taha greeting", 175, 195);
		
		Shift shift112 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd6,pd4),
				List.of(emp4),
				List.of(tf6,tf2),
				"mohammed greeting", 175, 195);
		
		Shift shift113 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				"mahmoud greeting", 175, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 6);
		d = new Date(cal.getTime().getTime());
		
		Shift shift121 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp3,emp5),
				List.of(tf4,tf3),
				"taha and ehab greeting", 185, 205);
		
		Shift shift122 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 195, 205);
		
		Shift shift123 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 177, 197);
		
		cal.set(Calendar.DAY_OF_MONTH, 7);
		d = new Date(cal.getTime().getTime());
		
		Shift shift131 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);
		
		Shift shift132 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);
		
		Shift shift133 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);
		
		cal.set(Calendar.DAY_OF_MONTH, 8);
		d = new Date(cal.getTime().getTime());
		
		Shift shift141 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift142 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				"ehab greeting", 192, 202);
		
		Shift shift143 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 9);
		d = new Date(cal.getTime().getTime());
		
		Shift shift151 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift152 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				"ehab and mohammed greeting", 192, 202);
		
		Shift shift153 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 10);
		d = new Date(cal.getTime().getTime());
		
		Shift shift161 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift162 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 192, 202);
		
		Shift shift163 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				"ehab greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 11);
		d = new Date(cal.getTime().getTime());
		
		Shift shift171 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2,pd15,pd16,pd17,pd18,pd19,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift172 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				"mohammed greeting", 192, 202);
		
		Shift shift173 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				"mahmoud and ehab greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 12);
		d = new Date(cal.getTime().getTime());
		
		Shift shift211 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				"taha greeting", 175, 195);
		
		Shift shift212 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd6,pd4),
				List.of(emp4),
				List.of(tf6,tf2),
				"mohammed greeting", 175, 195);
		
		Shift shift213 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				"mahmoud greeting", 175, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 13);
		d = new Date(cal.getTime().getTime());
		
		Shift shift221 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp3,emp5),
				List.of(tf4,tf3),
				"taha and ehab greeting", 185, 205);
		
		Shift shift222 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 195, 205);
		
		Shift shift223 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 177, 197);
		
		cal.set(Calendar.DAY_OF_MONTH, 14);
		d = new Date(cal.getTime().getTime());
		
		Shift shift231 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);
		
		Shift shift232 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);
		
		Shift shift233 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);
		
		cal.set(Calendar.DAY_OF_MONTH, 15);
		d = new Date(cal.getTime().getTime());
		
		Shift shift241 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift242 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				"ehab greeting", 192, 202);
		
		Shift shift243 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 16);
		d = new Date(cal.getTime().getTime());
		
		Shift shift251 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift252 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				"ehab and mohammed greeting", 192, 202);
		
		Shift shift253 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 17);
		d = new Date(cal.getTime().getTime());
		
		Shift shift261 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift262 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 192, 202);
		
		Shift shift263 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				"ehab greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 18);
		d = new Date(cal.getTime().getTime());
		
		Shift shift271 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2,pd15,pd16,pd17,pd18,pd19,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift272 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				"mohammed greeting", 192, 202);
		
		Shift shift273 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				"mahmoud and ehab greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 19);
		d = new Date(cal.getTime().getTime());
		
		Shift shift311 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				"taha greeting", 175, 195);
		
		Shift shift312 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd6,pd4),
				List.of(emp4),
				List.of(tf6,tf2),
				"mohammed greeting", 175, 195);
		
		Shift shift313 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				"mahmoud greeting", 175, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 20);
		d = new Date(cal.getTime().getTime());
		
		Shift shift321 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp3,emp5),
				List.of(tf4,tf3),
				"taha and ehab greeting", 185, 205);
		
		Shift shift322 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 195, 205);
		
		Shift shift323 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 177, 197);
		
		cal.set(Calendar.DAY_OF_MONTH, 21);
		d = new Date(cal.getTime().getTime());
		
		Shift shift331 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);
		
		Shift shift332 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);
		
		Shift shift333 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);
		
		cal.set(Calendar.DAY_OF_MONTH, 22);
		d = new Date(cal.getTime().getTime());
		
		Shift shift341 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift342 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				"ehab greeting", 192, 202);
		
		Shift shift343 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 23);
		d = new Date(cal.getTime().getTime());
		
		Shift shift351 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift352 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				"ehab and mohammed greeting", 192, 202);
		
		Shift shift353 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 24);
		d = new Date(cal.getTime().getTime());
		
		Shift shift361 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift362 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 192, 202);
		
		Shift shift363 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				"ehab greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 25);
		d = new Date(cal.getTime().getTime());
		
		Shift shift371 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2,pd15,pd16,pd17,pd18,pd19,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift372 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				"mohammed greeting", 192, 202);
		
		Shift shift373 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				"mahmoud and ehab greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 26);
		d = new Date(cal.getTime().getTime());
		
		Shift shift411 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				"taha greeting", 175, 195);
		
		Shift shift412 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd6,pd4),
				List.of(emp4),
				List.of(tf6,tf2),
				"mohammed greeting", 175, 195);
		
		Shift shift413 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				"mahmoud greeting", 175, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 27);
		d = new Date(cal.getTime().getTime());
		
		Shift shift421 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp3,emp5),
				List.of(tf4,tf3),
				"taha and ehab greeting", 185, 205);
		
		Shift shift422 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 195, 205);
		
		Shift shift423 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 177, 197);
		
		cal.set(Calendar.DAY_OF_MONTH, 28);
		d = new Date(cal.getTime().getTime());
		
		Shift shift431 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);
		
		Shift shift432 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);
		
		Shift shift433 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);
		
		cal.set(Calendar.DAY_OF_MONTH, 29);
		d = new Date(cal.getTime().getTime());
		
		Shift shift441 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift442 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				"ehab greeting", 192, 202);
		
		Shift shift443 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 30);
		d = new Date(cal.getTime().getTime());
		
		Shift shift451 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift452 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				"ehab and mohammed greeting", 192, 202);
		
		Shift shift453 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);
		
		cal.set(Calendar.DAY_OF_MONTH, 31);
		d = new Date(cal.getTime().getTime());
		
		Shift shift461 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift462 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 192, 202);
		
		Shift shift463 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				"ehab greeting", 173, 195);
		
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		d = new Date(cal.getTime().getTime());
		
		Shift shift471 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2,pd15,pd16,pd17,pd18,pd19,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);
		
		Shift shift472 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				"mohammed greeting", 192, 202);
		
		Shift shift473 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				"mahmoud and ehab greeting", 173, 195);
		
		shiftRepo.saveAll(List.of(
				shift111,shift112,shift113,
				shift121,shift122,shift123,
				shift131,shift132,shift133,
				shift141,shift142,shift143,
				shift151,shift152,shift153,
				shift161,shift162,shift163,
				shift171,shift172,shift173,
				shift211,shift212,shift213,
				shift221,shift222,shift223,
				shift231,shift232,shift233,
				shift241,shift242,shift243,
				shift251,shift252,shift253,
				shift261,shift262,shift263,
				shift271,shift272,shift273,
				shift311,shift312,shift313,
				shift321,shift322,shift323,
				shift331,shift332,shift333,
				shift341,shift342,shift343,
				shift351,shift352,shift353,
				shift361,shift362,shift363,
				shift371,shift372,shift373,
				shift411,shift412,shift413,
				shift421,shift422,shift423,
				shift431,shift432,shift433,
				shift441,shift442,shift443,
				shift451,shift452,shift453,
				shift461,shift462,shift463,
				shift471,shift472,shift473));
	}

	public void emp() {
		Day.getDays(shiftRepo.findLast(3 * 3,false)).forEach((k , v) ->{
			System.out.println("key is"+k+" and values is "+v.toString());
		});
	}
}