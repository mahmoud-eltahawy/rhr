package com.rhr.heat.service;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.enums.EmployeePosition;
import com.rhr.heat.enums.Machine;
import com.rhr.heat.enums.ShiftOrder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Tester {
	private final EmployeeRepo employeeRepo;
	private final ShiftRepo shiftRepo;
	private final ProblemRepo problemRepo;
	private final ReportService service;

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

		Problem P1 = new Problem("title 1", "description 1");
		Problem P2 = new Problem("title 2", "description 2");
		Problem P3 = new Problem("title 3", "description 3");
		Problem P4 = new Problem("title 4", "description 4");
		Problem P5 = new Problem("title 5", "description 5");
		Problem P6 = new Problem("title 6", "description 6");
		Problem P7 = new Problem("title 7", "description 7");
		Problem P8 = new Problem("title 8", "description 8");
		Problem P9 = new Problem("title 9", "description 9");

		problemRepo.save(P1);
		problemRepo.save(P2);
		problemRepo.save(P3);
		problemRepo.save(P4);
		problemRepo.save(P5);
		problemRepo.save(P6);
		problemRepo.save(P7);
		problemRepo.save(P8);
		problemRepo.save(P9);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 45);
		cal.set(Calendar.AM_PM, Calendar.AM);
		Time begin = new Time(cal.getTime().getTime());
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 40);
		Time end = new Time(cal.getTime().getTime());

		ProblemDetail pd1  = new ProblemDetail(null,List.of(P1,P2),Machine.ATM_ONE,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 50);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd2  = new ProblemDetail(null,List.of(P3,P4),Machine.ATM_TWO,begin,end);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 50);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd3  = new ProblemDetail(null,List.of(P5,P6),Machine.DRAYER_ONE,begin,end);
		cal.set(Calendar.HOUR, 6);
		cal.set(Calendar.MINUTE, 6);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd4  = new ProblemDetail(null,List.of(P7,P8),Machine.DRAYER_TWO,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 5);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd5  = new ProblemDetail(null,List.of(P9,P1),Machine.DRAYER_THREE,begin,end);
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 30);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd6  = new ProblemDetail(null,List.of(P2,P5),Machine.DRAYER_FOUR,begin,end);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 5);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd7  = new ProblemDetail(null,List.of(P4,P7),Machine.DRAYER_FIVE,begin,end);
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 40);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd8  = new ProblemDetail(null,List.of(P8,P3),Machine.DRAYER_SIX,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 30);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd9  = new ProblemDetail(null,List.of(P4,P6),Machine.DRAYER_SEVEN,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 10);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd10 = new ProblemDetail(null,List.of(P5,P7),Machine.ATM_ONE,begin,end);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 30);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd11 = new ProblemDetail(null,List.of(P1,P8),Machine.ATM_TWO,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 20);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd12 = new ProblemDetail(null,List.of(P1,P8,P3),Machine.PROJECT,begin,end);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 45);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd13 = new ProblemDetail(null,List.of(P2,P9,P4),Machine.PROJECT,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd14 = new ProblemDetail(null,List.of(P5,P6,P7),Machine.KILEN_FIVE,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd15 = new ProblemDetail(null,List.of(P5,P6,P7),Machine.KILEN_ONE,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd16 = new ProblemDetail(null,List.of(P5,P6,P7),Machine.KILEN_ONE,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd17 = new ProblemDetail(null,List.of(P5,P6,P7),Machine.KILEN_TWO,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd18 = new ProblemDetail(null,List.of(P5,P6,P7),Machine.KILEN_THREE,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd19 = new ProblemDetail(null,List.of(P5,P6,P7),Machine.KILEN_FOUR,begin,end);


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
		cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 28);
		Date d = new Date(cal.getTime().getTime());

		Shift shift011 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				"taha greeting", 175, 195);

		Shift shift012 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd6,pd4),
				List.of(emp4),
				List.of(tf6,tf2),
				"mohammed greeting", 175, 195);

		Shift shift013 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				"mahmoud greeting", 175, 195);

		cal.set(Calendar.DAY_OF_MONTH, 29);
		d = new Date(cal.getTime().getTime());

		Shift shift021 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp3,emp5),
				List.of(tf4,tf3),
				"taha and ehab greeting", 185, 205);

		Shift shift022 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 195, 205);

		Shift shift023 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 177, 197);

		cal.set(Calendar.DAY_OF_MONTH, 30);
		d = new Date(cal.getTime().getTime());

		Shift shift031 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);

		Shift shift032 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);

		Shift shift033 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);

		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		d = new Date(cal.getTime().getTime());

		Shift shift041 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift042 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				"ehab greeting", 192, 202);

		Shift shift043 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 2);
		d = new Date(cal.getTime().getTime());

		Shift shift051 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift052 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				"ehab and mohammed greeting", 192, 202);

		Shift shift053 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 3);
		d = new Date(cal.getTime().getTime());

		Shift shift061 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift062 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 192, 202);

		Shift shift063 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				"ehab greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 4);
		d = new Date(cal.getTime().getTime());

		Shift shift071 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2,pd15,pd16,pd17,pd18,pd19,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift072 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				"mohammed greeting", 192, 202);

		Shift shift073 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				"mahmoud and ehab greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 5);
		d = new Date(cal.getTime().getTime());

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


		cal.set(Calendar.DAY_OF_MONTH, 2);
		d = new Date(cal.getTime().getTime());

		Shift shift511 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);

		Shift shift512 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);

		Shift shift513 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);
		

		cal.set(Calendar.DAY_OF_MONTH, 3);
		d = new Date(cal.getTime().getTime());

		Shift shift521 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);

		Shift shift522 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);

		Shift shift523 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);
		
		cal.set(Calendar.DAY_OF_MONTH, 4);
		d = new Date(cal.getTime().getTime());

		Shift shift531 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);

		Shift shift532 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);

		Shift shift533 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);

		cal.set(Calendar.DAY_OF_MONTH, 5);
		d = new Date(cal.getTime().getTime());

		Shift shift541 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift542 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				"ehab greeting", 192, 202);

		Shift shift543 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 6);
		d = new Date(cal.getTime().getTime());

		Shift shift551 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift552 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				"ehab and mohammed greeting", 192, 202);

		Shift shift553 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 7);
		d = new Date(cal.getTime().getTime());

		Shift shift561 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift562 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 192, 202);

		Shift shift563 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				"ehab greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 8);
		d = new Date(cal.getTime().getTime());

		Shift shift571 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2,pd15,pd16,pd17,pd18,pd19,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift572 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				"mohammed greeting", 192, 202);

		Shift shift573 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				"mahmoud and ehab greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 9);
		d = new Date(cal.getTime().getTime());

		Shift shift611 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				"taha greeting", 175, 195);

		Shift shift612 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd6,pd4),
				List.of(emp4),
				List.of(tf6,tf2),
				"mohammed greeting", 175, 195);

		Shift shift613 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				"mahmoud greeting", 175, 195);

		cal.set(Calendar.DAY_OF_MONTH, 10);
		d = new Date(cal.getTime().getTime());

		Shift shift621 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp3,emp5),
				List.of(tf4,tf3),
				"taha and ehab greeting", 185, 205);

		Shift shift622 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 195, 205);

		Shift shift623 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 177, 197);

		cal.set(Calendar.DAY_OF_MONTH, 11);
		d = new Date(cal.getTime().getTime());

		Shift shift631 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);

		Shift shift632 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);

		Shift shift633 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);

		cal.set(Calendar.DAY_OF_MONTH, 12);
		d = new Date(cal.getTime().getTime());

		Shift shift641 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift642 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				"ehab greeting", 192, 202);

		Shift shift643 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 13);
		d = new Date(cal.getTime().getTime());

		Shift shift651 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift652 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				"ehab and mohammed greeting", 192, 202);

		Shift shift653 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 14);
		d = new Date(cal.getTime().getTime());

		Shift shift661 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift662 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 192, 202);

		Shift shift663 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				"ehab greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 15);
		d = new Date(cal.getTime().getTime());

		Shift shift671 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2,pd15,pd16,pd17,pd18,pd19,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift672 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				"mohammed greeting", 192, 202);

		Shift shift673 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				"mahmoud and ehab greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 16);
		d = new Date(cal.getTime().getTime());

		Shift shift711 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				"taha greeting", 175, 195);

		Shift shift712 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd6,pd4),
				List.of(emp4),
				List.of(tf6,tf2),
				"mohammed greeting", 175, 195);

		Shift shift713 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				"mahmoud greeting", 175, 195);

		cal.set(Calendar.DAY_OF_MONTH, 17);
		d = new Date(cal.getTime().getTime());

		Shift shift721 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp3,emp5),
				List.of(tf4,tf3),
				"taha and ehab greeting", 185, 205);

		Shift shift722 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 195, 205);

		Shift shift723 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 177, 197);

		cal.set(Calendar.DAY_OF_MONTH, 18);
		d = new Date(cal.getTime().getTime());

		Shift shift731 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);

		Shift shift732 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);

		Shift shift733 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);

		cal.set(Calendar.DAY_OF_MONTH, 19);
		d = new Date(cal.getTime().getTime());

		Shift shift741 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift742 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				"ehab greeting", 192, 202);

		Shift shift743 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 20);
		d = new Date(cal.getTime().getTime());

		Shift shift751 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift752 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				"ehab and mohammed greeting", 192, 202);

		Shift shift753 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 21);
		d = new Date(cal.getTime().getTime());

		Shift shift761 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift762 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 192, 202);

		Shift shift763 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				"ehab greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 22);
		d = new Date(cal.getTime().getTime());

		Shift shift771 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2,pd15,pd16,pd17,pd18,pd19,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift772 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				"mohammed greeting", 192, 202);

		Shift shift773 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				"mahmoud and ehab greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 23);
		d = new Date(cal.getTime().getTime());

		Shift shift811 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				"taha greeting", 175, 195);

		Shift shift812 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd6,pd4),
				List.of(emp4),
				List.of(tf6,tf2),
				"mohammed greeting", 175, 195);

		Shift shift813 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				"mahmoud greeting", 175, 195);

		cal.set(Calendar.DAY_OF_MONTH, 24);
		d = new Date(cal.getTime().getTime());

		Shift shift821 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp3,emp5),
				List.of(tf4,tf3),
				"taha and ehab greeting", 185, 205);

		Shift shift822 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 195, 205);

		Shift shift823 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 177, 197);

		cal.set(Calendar.DAY_OF_MONTH, 25);
		d = new Date(cal.getTime().getTime());

		Shift shift831 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);

		Shift shift832 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);

		Shift shift833 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);

		cal.set(Calendar.DAY_OF_MONTH, 26);
		d = new Date(cal.getTime().getTime());

		Shift shift841 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift842 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				"ehab greeting", 192, 202);

		Shift shift843 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 27);
		d = new Date(cal.getTime().getTime());

		Shift shift851 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift852 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				"ehab and mohammed greeting", 192, 202);

		Shift shift853 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 28);
		d = new Date(cal.getTime().getTime());

		Shift shift861 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift862 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 192, 202);

		Shift shift863 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				"ehab greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 29);
		d = new Date(cal.getTime().getTime());

		Shift shift871 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2,pd15,pd16,pd17,pd18,pd19,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift872 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				"mohammed greeting", 192, 202);

		Shift shift873 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				"mahmoud and ehab greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 30);
		d = new Date(cal.getTime().getTime());

		Shift shift911 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				"taha greeting", 175, 195);

		Shift shift912 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd6,pd4),
				List.of(emp4),
				List.of(tf6,tf2),
				"mohammed greeting", 175, 195);

		Shift shift913 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				"mahmoud greeting", 175, 195);

		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		d = new Date(cal.getTime().getTime());

		Shift shift921 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp3,emp5),
				List.of(tf4,tf3),
				"taha and ehab greeting", 185, 205);

		Shift shift922 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 195, 205);

		Shift shift923 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 177, 197);

		cal.set(Calendar.DAY_OF_MONTH, 2);
		d = new Date(cal.getTime().getTime());

		Shift shift931 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"ehab greeting", 188, 206);

		Shift shift932 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 196, 206);

		Shift shift933 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 171, 193);

		cal.set(Calendar.DAY_OF_MONTH, 3);
		d = new Date(cal.getTime().getTime());

		Shift shift941 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift942 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				"ehab greeting", 192, 202);

		Shift shift943 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 4);
		d = new Date(cal.getTime().getTime());

		Shift shift951 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift952 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				"ehab and mohammed greeting", 192, 202);

		Shift shift953 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				"mahmoud greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 5);
		d = new Date(cal.getTime().getTime());

		Shift shift961 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift962 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				"mohammed greeting", 192, 202);

		Shift shift963 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				"ehab greeting", 173, 195);

		cal.set(Calendar.DAY_OF_MONTH, 6);
		d = new Date(cal.getTime().getTime());

		Shift shift971 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2,pd15,pd16,pd17,pd18,pd19,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				"taha greeting", 181, 201);

		Shift shift972 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				"mohammed greeting", 192, 202);

		Shift shift973 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				"mahmoud and ehab greeting", 173, 195);


		shiftRepo.saveAll(List.of(
				shift011,shift012,shift013,
				shift021,shift022,shift023,
				shift031,shift032,shift033,
				shift041,shift042,shift043,
				shift051,shift052,shift053,
				shift061,shift062,shift063,
				shift071,shift072,shift073,
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
				shift471,shift472,shift473,
				shift511,shift512,shift513,
				shift521,shift522,shift523,
				shift531,shift532,shift533,
				shift541,shift542,shift543,
				shift551,shift552,shift553,
				shift561,shift562,shift563,
				shift571,shift572,shift573,
				shift611,shift612,shift613,
				shift621,shift622,shift623,
				shift631,shift632,shift633,
				shift641,shift642,shift643,
				shift651,shift652,shift653,
				shift661,shift662,shift663,
				shift671,shift672,shift673,
				shift711,shift712,shift713,
				shift721,shift722,shift723,
				shift731,shift732,shift733,
				shift741,shift742,shift743,
				shift751,shift752,shift753,
				shift761,shift762,shift763,
				shift771,shift772,shift773,
				shift811,shift812,shift813,
				shift821,shift822,shift823,
				shift831,shift832,shift833,
				shift841,shift842,shift843,
				shift851,shift852,shift853,
				shift861,shift862,shift863,
				shift871,shift872,shift873,
				shift911,shift912,shift913,
				shift921,shift922,shift923,
				shift931,shift932,shift933,
				shift941,shift942,shift943,
				shift951,shift952,shift953,
				shift961,shift962,shift963,
				shift971,shift972,shift973));
	}

	public void emp() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 45);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.AM_PM, Calendar.AM);
		Time begin = new Time(cal.getTime().getTime());
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 40);
		Time end = new Time(cal.getTime().getTime());
		ProblemDetail pd1  = new ProblemDetail(null,null,Machine.ATM_ONE,begin,end);
		service.addProblem(pd1);
		
	}
}
