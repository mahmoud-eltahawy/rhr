package com.rhr.heat.service.api;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rhr.heat.Tools;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.enums.EmployeePosition;
import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.service.CommonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Tester {
	private final EmployeeRepo employeeRepo;
	private final ShiftRepo shiftRepo;
	private final ProblemRepo problemRepo;
	private final MachineRepo machineRepo;
	private final CommonService commonService;

	public void insertData() {
		Employee emp1 = new Employee(null,"mahmoud","mohammed","sabry",EmployeePosition.Engineer,"mahmoud_sabry","1234");
		Employee emp2 = new Employee(null,"mahmoud","gamal","mohammed",EmployeePosition.worker,"mahmoud_gamal","1234");
		Employee emp3 = new Employee(null,"taha","mohammed","ismaail",EmployeePosition.worker,"taha_mohammed","1234");
		Employee emp4 = new Employee(null,"mohammed","gomaa","mohammed",EmployeePosition.worker,"mohammed_gomaa","1234");
		Employee emp5 = new Employee(null,"ehab","hagag","saad",EmployeePosition.worker,"ehab_hagag","1234");
		
		Machine KILEN_ONE     = new Machine(null, "KILEN" , 1);
		Machine KILEN_TWO     = new Machine(null, "KILEN" , 2);
		Machine KILEN_THREE   = new Machine(null, "KILEN" , 3);
		Machine KILEN_FOUR    = new Machine(null, "KILEN" , 4);
		Machine KILEN_FIVE    = new Machine(null, "KILEN" , 5);
		Machine DRAYER_ONE    = new Machine(null, "DRAYER", 1);
		Machine DRAYER_TWO    = new Machine(null, "DRAYER", 2);
		Machine DRAYER_THREE  = new Machine(null, "DRAYER", 3);
		Machine DRAYER_FOUR   = new Machine(null, "DRAYER", 4);
		Machine DRAYER_FIVE   = new Machine(null, "DRAYER", 5);
		Machine DRAYER_SIX    = new Machine(null, "DRAYER", 6);
		Machine DRAYER_SEVEN  = new Machine(null, "DRAYER", 7);
		Machine ATM_ONE       = new Machine(null, "ATM"   , 1);
		Machine ATM_TWO       = new Machine(null, "ATM"   , 2);
		Machine PROJECT       = new Machine(null, "PROJECT", 0);

		KILEN_ONE.setId(machineRepo.save(KILEN_ONE));
		KILEN_TWO.setId(machineRepo.save(KILEN_TWO));
		KILEN_THREE.setId(machineRepo.save(KILEN_THREE));
		KILEN_FOUR.setId(machineRepo.save(KILEN_FOUR));
		KILEN_FIVE.setId(machineRepo.save(KILEN_FIVE));
		DRAYER_ONE.setId(machineRepo.save(DRAYER_ONE));
		DRAYER_TWO.setId(machineRepo.save(DRAYER_TWO));
		DRAYER_THREE.setId(machineRepo.save(DRAYER_THREE));
		DRAYER_FOUR.setId(machineRepo.save(DRAYER_FOUR));
		DRAYER_FIVE.setId(machineRepo.save(DRAYER_FIVE));
		DRAYER_SIX.setId(machineRepo.save(DRAYER_SIX));
		DRAYER_SEVEN.setId(machineRepo.save(DRAYER_SEVEN));
		ATM_ONE.setId(machineRepo.save(ATM_ONE));
		ATM_TWO.setId(machineRepo.save(ATM_TWO));
		PROJECT.setId(machineRepo.save(PROJECT));

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

		ProblemDetail pd1  = new ProblemDetail(null,List.of(P1,P2),ATM_ONE,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 50);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd2  = new ProblemDetail(null,List.of(P3,P4),ATM_TWO,begin,end);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 50);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd3  = new ProblemDetail(null,List.of(P5,P6),DRAYER_ONE,begin,end);
		cal.set(Calendar.HOUR, 6);
		cal.set(Calendar.MINUTE, 6);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd4  = new ProblemDetail(null,List.of(P7,P8),DRAYER_TWO,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 5);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd5  = new ProblemDetail(null,List.of(P9,P1),DRAYER_THREE,begin,end);
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 30);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd6  = new ProblemDetail(null,List.of(P2,P5),DRAYER_FOUR,begin,end);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 5);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd7  = new ProblemDetail(null,List.of(P4,P7),DRAYER_FIVE,begin,end);
		cal.set(Calendar.HOUR, 3);
		cal.set(Calendar.MINUTE, 40);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd8  = new ProblemDetail(null,List.of(P8,P3),DRAYER_SIX,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 30);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd9  = new ProblemDetail(null,List.of(P4,P6),DRAYER_SEVEN,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 10);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd10 = new ProblemDetail(null,List.of(P5,P7),ATM_ONE,begin,end);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 30);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd11 = new ProblemDetail(null,List.of(P1,P8),ATM_TWO,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 20);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd12 = new ProblemDetail(null,List.of(P1,P8,P3),PROJECT,begin,end);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 45);
		begin = new Time(cal.getTime().getTime());
		ProblemDetail pd13 = new ProblemDetail(null,List.of(P2,P9,P4),PROJECT,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd14 = new ProblemDetail(null,List.of(P5,P6,P7),KILEN_FIVE,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd15 = new ProblemDetail(null,List.of(P5,P6,P7),KILEN_ONE,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd16 = new ProblemDetail(null,List.of(P5,P6,P7),KILEN_ONE,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd17 = new ProblemDetail(null,List.of(P5,P6,P7),KILEN_TWO,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd18 = new ProblemDetail(null,List.of(P5,P6,P7),KILEN_THREE,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		ProblemDetail pd19 = new ProblemDetail(null,List.of(P5,P6,P7),KILEN_FOUR,begin,end);


		TotalFlow tf1 = new TotalFlow(null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end);
		cal.set(Calendar.HOUR, 8);
		cal.set(Calendar.MINUTE, 0);
		end = new Time(cal.getTime().getTime());
		TotalFlow tf2 = new TotalFlow(null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 35);
		begin = new Time(cal.getTime().getTime());
		TotalFlow tf3 = new TotalFlow(null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end);
		cal.set(Calendar.HOUR, 5);
		cal.set(Calendar.MINUTE, 15);
		end = new Time(cal.getTime().getTime());
		TotalFlow tf4 = new TotalFlow(null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end);
		cal.set(Calendar.HOUR, 1);
		cal.set(Calendar.MINUTE, 15);
		begin = new Time(cal.getTime().getTime());
		TotalFlow tf5 = new TotalFlow(null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end);
		cal.set(Calendar.HOUR, 4);
		cal.set(Calendar.MINUTE, 10);
		end = new Time(cal.getTime().getTime());
		TotalFlow tf6 = new TotalFlow(null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end);
		cal.set(Calendar.HOUR, 00);
		cal.set(Calendar.MINUTE, 15);
		begin = new Time(cal.getTime().getTime());
		TotalFlow tf7 = new TotalFlow(null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end);

		
		
		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2022);
		cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 28);
		Date d = new Date(cal.getTime().getTime());

		Shift shift011 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift012 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd6,pd4),
				List.of(emp4),
				List.of(tf6,tf2),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift013 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		cal.set(Calendar.DAY_OF_MONTH, 29);
		d = new Date(cal.getTime().getTime());

		Shift shift021 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp3,emp5),
				List.of(tf4,tf3),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift022 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift023 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		cal.set(Calendar.DAY_OF_MONTH, 30);
		d = new Date(cal.getTime().getTime());

		Shift shift031 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift032 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp4),
				List.of(tf1,tf2),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift033 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		d = new Date(cal.getTime().getTime());

		Shift shift041 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd2,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift042 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd3,pd4),
				List.of(emp5),
				List.of(tf1,tf2),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift043 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		cal.set(Calendar.DAY_OF_MONTH, 2);
		d = new Date(cal.getTime().getTime());

		Shift shift051 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd10,pd9),
				List.of(emp5),
				List.of(tf4,tf3),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift052 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd7,pd8),
				List.of(emp4,emp5),
				List.of(tf1,tf2),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift053 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf4,tf5),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		cal.set(Calendar.DAY_OF_MONTH, 3);
		d = new Date(cal.getTime().getTime());

		Shift shift061 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd13,pd12),
				List.of(emp5),
				List.of(tf4,tf3),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift062 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf1,tf2),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift063 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd11,pd12),
				List.of(emp5),
				List.of(tf4,tf5),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		cal.set(Calendar.DAY_OF_MONTH, 4);
		d = new Date(cal.getTime().getTime());

		Shift shift071 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2,pd15,pd16,pd17,pd18,pd19,pd3),
				List.of(emp5),
				List.of(tf4,tf3),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift072 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd13,pd14),
				List.of(emp4),
				List.of(tf5,tf3),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift073 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd12,pd13),
				List.of(emp2,emp5),
				List.of(tf7,tf5),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		cal.set(Calendar.DAY_OF_MONTH, 5);
		d = new Date(cal.getTime().getTime());

		Shift shift111 = new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(pd1,pd2),
				List.of(emp3),
				List.of(tf1,tf2),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift112 = new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(pd6,pd4),
				List.of(emp4),
				List.of(tf6,tf2),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		Shift shift113 = new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(pd5,pd6),
				List.of(emp2),
				List.of(tf1,tf2),
				List.of(new Temperature(null,PROJECT,205,195),
						new Temperature(null,KILEN_FIVE,206,194)),
				List.of("greeting"));

		shiftRepo.saveAll(List.of(
				shift011,shift012,shift013,
				shift021,shift022,shift023,
				shift031,shift032,shift033,
				shift041,shift042,shift043,
				shift051,shift052,shift053,
				shift061,shift062,shift063,
				shift071,shift072,shift073,
				shift111,shift112,shift113));
	}

	public Object emp() {
		return commonService.getCategoryMachines(shiftRepo.findLast(2, true).get(1).getProblems());
	}
}
