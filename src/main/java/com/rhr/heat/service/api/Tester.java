package com.rhr.heat.service.api;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.dao.topLayer.ShiftRepo;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.enums.EmployeePosition;
import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.service.Dealer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Tester {
	private final EmployeeRepo employeeRepo;
	private final ShiftRepo shiftRepo;
	private final ProblemRepo problemRepo;
	private final MachineRepo machineRepo;
	private final Dealer commonService;

	public void insertData() {
		Employee emp2 = new Employee(UUID.randomUUID(),"mahmoud","gamal","mohammed",EmployeePosition.worker,"mahmoud_gamal","1234");
		Employee emp3 = new Employee(UUID.randomUUID(),"taha","mohammed","ismaail",EmployeePosition.worker,"taha_mohammed","1234");
		Employee emp4 = new Employee(UUID.randomUUID(),"mohammed","gomaa","mohammed",EmployeePosition.worker,"mohammed_gomaa","1234");
		Employee emp5 = new Employee(UUID.randomUUID(),"ehab","hagag","saad",EmployeePosition.worker,"ehab_hagag","1234");
		
		employeeRepo.saveAll(List.of(emp2,emp3,emp4,emp5));
		
		Machine KILEN_ONE     = new Machine(UUID.randomUUID(), "KILEN" , 1);
		Machine KILEN_TWO     = new Machine(UUID.randomUUID(), "KILEN" , 2);
		Machine KILEN_THREE   = new Machine(UUID.randomUUID(), "KILEN" , 3);
		Machine KILEN_FOUR    = new Machine(UUID.randomUUID(), "KILEN" , 4);
		Machine KILEN_FIVE    = new Machine(UUID.randomUUID(), "KILEN" , 5);
		Machine DRAYER_ONE    = new Machine(UUID.randomUUID(), "DRAYER", 1);
		Machine DRAYER_TWO    = new Machine(UUID.randomUUID(), "DRAYER", 2);
		Machine DRAYER_THREE  = new Machine(UUID.randomUUID(), "DRAYER", 3);
		Machine DRAYER_FOUR   = new Machine(UUID.randomUUID(), "DRAYER", 4);
		Machine DRAYER_FIVE   = new Machine(UUID.randomUUID(), "DRAYER", 5);
		Machine DRAYER_SIX    = new Machine(UUID.randomUUID(), "DRAYER", 6);
		Machine DRAYER_SEVEN  = new Machine(UUID.randomUUID(), "DRAYER", 7);
		Machine ATM_ONE       = new Machine(UUID.randomUUID(), "ATM"   , 1);
		Machine ATM_TWO       = new Machine(UUID.randomUUID(), "ATM"   , 2);
		Machine PROJECT       = new Machine(UUID.randomUUID(), "PROJECT", 0);

		machineRepo.saveAll(List.of(KILEN_ONE,KILEN_TWO,KILEN_THREE,KILEN_FOUR,KILEN_FIVE,
				DRAYER_ONE,DRAYER_TWO,DRAYER_THREE,DRAYER_FOUR,DRAYER_FIVE,DRAYER_SIX,DRAYER_SEVEN,
				ATM_ONE,ATM_TWO,PROJECT));
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
		
		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2022);
		cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 28);
		Date d = new Date(cal.getTime().getTime());

		Shift shift011 =
				new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(new ProblemDetail(UUID.randomUUID(),null,List.of(P5,P6,P7),KILEN_FOUR,begin,end)),
				List.of(emp2),
				List.of(new TotalFlow(UUID.randomUUID(),null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end)),
				List.of(new Temperature(UUID.randomUUID(),null,PROJECT,205,195),
						new Temperature(UUID.randomUUID(),null,KILEN_FIVE,206,194)),
				List.of(new Note(null,"greeting")));

		Shift shift012 =
				new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(new ProblemDetail(UUID.randomUUID(),null,List.of(P5,P6,P7),KILEN_FOUR,begin,end)),
				List.of(emp3),
				List.of(new TotalFlow(UUID.randomUUID(),null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end)),
				List.of(new Temperature(UUID.randomUUID(),null,PROJECT,205,195),
						new Temperature(UUID.randomUUID(),null,KILEN_FIVE,206,194)),
				List.of(new Note(null,"greeting")));

		Shift shift013 =
				new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(new ProblemDetail(UUID.randomUUID(),null,List.of(P5,P6,P7),KILEN_FOUR,begin,end)),
				List.of(emp4),
				List.of(new TotalFlow(UUID.randomUUID(),null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end)),
				List.of(new Temperature(UUID.randomUUID(),null,PROJECT,205,195),
						new Temperature(UUID.randomUUID(),null,KILEN_FIVE,206,194)),
				List.of(new Note(null,"greeting")));

		cal.set(Calendar.DAY_OF_MONTH, 29);
		d = new Date(cal.getTime().getTime());

		Shift shift021 =
				new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(new ProblemDetail(UUID.randomUUID(),null,List.of(P5,P6,P7),KILEN_FOUR,begin,end)),
				List.of(emp5),
				List.of(new TotalFlow(UUID.randomUUID(),null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end)),
				List.of(new Temperature(UUID.randomUUID(),null,PROJECT,205,195),
						new Temperature(UUID.randomUUID(),null,KILEN_FIVE,206,194)),
				List.of(new Note(null,"greeting")));

		Shift shift022 =
				new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(new ProblemDetail(UUID.randomUUID(),null,List.of(P5,P6,P7),KILEN_FOUR,begin,end)),
				List.of(emp2),
				List.of(new TotalFlow(UUID.randomUUID(),null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end)),
				List.of(new Temperature(UUID.randomUUID(),null,PROJECT,205,195),
						new Temperature(UUID.randomUUID(),null,KILEN_FIVE,206,194)),
				List.of(new Note(null,"greeting")));

		Shift shift023 =
				new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(new ProblemDetail(UUID.randomUUID(),null,List.of(P5,P6,P7),KILEN_FOUR,begin,end)),
				List.of(emp3),
				List.of(new TotalFlow(UUID.randomUUID(),null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end)),
				List.of(new Temperature(UUID.randomUUID(),null,PROJECT,205,195),
						new Temperature(UUID.randomUUID(),null,KILEN_FIVE,206,194)),
				List.of(new Note(null,"greeting")));

		cal.set(Calendar.DAY_OF_MONTH, 30);
		d = new Date(cal.getTime().getTime());

		Shift shift031 =
				new Shift(new ShiftId(null,d,ShiftOrder.FIRST),
				List.of(new ProblemDetail(UUID.randomUUID(),null,List.of(P5,P6,P7),KILEN_FOUR,begin,end)),
				List.of(emp4),
				List.of(new TotalFlow(UUID.randomUUID(),null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end)),
				List.of(new Temperature(UUID.randomUUID(),null,PROJECT,205,195),
						new Temperature(UUID.randomUUID(),null,KILEN_FIVE,206,194)),
				List.of(new Note(null,"greeting")));

		Shift shift032 =
				new Shift(new ShiftId(null,d,ShiftOrder.SECOND),
				List.of(new ProblemDetail(UUID.randomUUID(),null,List.of(P5,P6,P7),KILEN_FOUR,begin,end)),
				List.of(emp5),
				List.of(new TotalFlow(UUID.randomUUID(),null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end)),
				List.of(new Temperature(UUID.randomUUID(),null,PROJECT,205,195),
						new Temperature(UUID.randomUUID(),null,KILEN_FIVE,206,194)),
				List.of(new Note(null,"greeting")));

		Shift shift033 =
				new Shift(new ShiftId(null,d,ShiftOrder.THIRD),
				List.of(new ProblemDetail(UUID.randomUUID(),null,List.of(P5,P6,P7),KILEN_FOUR,begin,end)),
				List.of(emp2),
				List.of(new TotalFlow(UUID.randomUUID(),null,List.of(ATM_ONE,DRAYER_TWO),77,92,begin,end)),
				List.of(new Temperature(UUID.randomUUID(),null,PROJECT,205,195),
						new Temperature(UUID.randomUUID(),null,KILEN_FIVE,206,194)),
				List.of(new Note(null,"greeting")));


		shiftRepo.saveAll(List.of(
				shift011,shift012,shift013,
				shift021,shift022,shift023,
				shift031,shift032,shift033))
		.forEach(s ->System.out.println(s));
	}

	public Object emp() {
		return commonService.getCategoryMachines(shiftRepo.findLast(2).get(1).getProblems());
	}
}
