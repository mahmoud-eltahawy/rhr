package com.rhr.heat.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shift {
	private ShiftId shiftId;
	private List<ProblemDetail> problems;
	private List<Employee> employees;
	private List<TotalFlow> totalFlowAverage;
	private List<Temperature> temps;
	private List<Note> notes;
	
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