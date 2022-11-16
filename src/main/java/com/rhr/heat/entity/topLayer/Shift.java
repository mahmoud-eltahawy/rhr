package com.rhr.heat.entity.topLayer;

import java.util.ArrayList;
import java.util.List;

import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.enums.Pushable;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Shift {
	@Setter
	protected ShiftId shiftId;
	@Setter
	private List<ProblemDetail> problems;
	@Setter
	private List<Employee> employees;
	@Setter
	private List<TotalFlow> totalFlowAverage;
	private List<Temperature> temps;
	private List<Note> notes;

	public Shift(ShiftId shiftId, List<ProblemDetail> problems, List<Employee> employees,
			List<TotalFlow> totalFlowAverage, List<Temperature> temps, List<Note> notes) {
		this.shiftId = shiftId;
		this.problems = problems;
		this.employees = employees;
		this.totalFlowAverage = totalFlowAverage;
		this.temps = temps;
		this.notes = notes;
		
		if(temps != null) {
			this.temps.forEach(t -> t.setShiftId(this.shiftId));
		}
		if(notes != null) {
			this.notes.forEach(n -> n.setShiftId(this.shiftId));
		}
	}

	public void setTemps(List<Temperature> temps) {
		this.temps = temps;
		
		if(temps != null) {
			this.temps.forEach(t -> t.setShiftId(this.shiftId));
		}
	}


	public void setNotes(List<Note> notes) {
		this.notes = notes;

		if(notes != null) {
			this.notes.forEach(n -> n.setShiftId(this.shiftId));
		}
	}
	

	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(shiftId != null) {
			canPush.addAll(shiftId.isPushable());
		} else {
			canPush.add(Pushable.SHIFT_DATE_AND_ORDER_ARE_NULL);
		}
		if(problems != null) {
			if(!problems.isEmpty()) {
				for (ProblemDetail pd : problems) {
					canPush.addAll(pd.isPushable());
				}
			}
		}
		if(employees != null) {
			if(!employees.isEmpty()) {
				for (Employee employee : employees) {
					canPush.addAll(employee.isPushable());
				}
			} else {
				canPush.add(Pushable.SHIFT_EMPLOYEES_LIST_IS_EMPTY);
			}
		} else {
			canPush.add(Pushable.SHIFT_EMPLOYEES_LIST_IS_NULL);
		}
		if(totalFlowAverage != null) {
			if(!totalFlowAverage.isEmpty()) {
				for (TotalFlow flow : totalFlowAverage) {
					canPush.addAll(flow.isPushable());
				}
			} else {
				canPush.add(Pushable.SHIFT_FLOW_LIST_IS_EMPTY);
			}
		} else {
			canPush.add(Pushable.SHIFT_FLOW_LIST_IS_NULL);
		}
		if(temps != null) {
			if(!temps.isEmpty()) {
				for (Temperature temp : temps) {
					canPush.addAll(temp.isPushable());
				}
			} else {
				canPush.add(Pushable.SHIFT_TEMPERATURE_LIST_IS_EMPTY);
			}
		} else {
			canPush.add(Pushable.SHIFT_TEMPERATURE_LIST_IS_NULL);
		}
		if(notes != null) {
			if(!notes.isEmpty()) {
				for (Temperature temp : temps) {
					canPush.addAll(temp.isPushable());
				}
			} 
		}
		return canPush;
	}

}