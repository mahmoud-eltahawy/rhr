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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shift {
	protected ShiftId shiftId;
	private List<ProblemDetail> problems;
	private List<Employee> employees;
	private List<TotalFlow> totalFlowAverage;
	private List<Temperature> temps;
	private List<Note> notes;

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