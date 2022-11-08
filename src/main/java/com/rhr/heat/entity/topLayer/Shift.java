package com.rhr.heat.entity.topLayer;

import java.util.List;

import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.entity.TotalFlow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Shift extends ShiftFamily {
	private List<ProblemDetail> problems;
	private List<Employee> employees;
	private List<TotalFlow> totalFlowAverage;
	private List<Temperature> temps;
	private List<Note> notes;

	//TODO remove this constructor
	public Shift(ShiftId shiftId, List<ProblemDetail> problems, List<Employee> employees,
			List<TotalFlow> totalFlowAverage, List<Temperature> temps, List<Note> notes) {
		super(shiftId);
		this.problems = problems;
		this.employees = employees;
		this.totalFlowAverage = totalFlowAverage;
		this.temps = temps;
		this.notes = notes;
	}
	
	public Boolean isPushable() {
		if(shiftId == null) {
			return false;
		}
		if(shiftId.getDate() == null || shiftId.getShift() == null) {
			return false;
		}
		if(employees == null) {
			return false;
		}
		if(employees.size() == 0) {
			return false;
		}
		if(totalFlowAverage == null) {
			return false;
		}
		if(totalFlowAverage.size() == 0) {
			return false;
		}
		if(temps == null) {
			return false;
		}
		if(temps == null) {
			return false;
		}
		return true;
	}

}