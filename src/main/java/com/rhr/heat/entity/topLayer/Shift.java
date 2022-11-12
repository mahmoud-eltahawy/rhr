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
	
	public Boolean isPushable() {
		if(shiftId != null && problems != null && employees != null &&
				totalFlowAverage != null && temps != null && notes != null) {
			if(!shiftId.isPushable()) {
				return false;
			} 
			for (Employee employee : employees) {
				if(!employee.isPushable()) {
					return false;
				}
			}
			for (ProblemDetail pd : problems) {
				if(!pd.isPushable()) {
					return false;
				}
			}
			for (TotalFlow tf : totalFlowAverage) {
				if(!tf.isPushable()) {
					return false;
				}
			}
			for (Temperature tmp :temps) {
				if(!tmp.isPushable()) {
					return false;
				}
			}
			for (Note n: notes) {
				if(!n.isPushable()) {
					return false;
				}
			}
			if(employees.size() > 0 && totalFlowAverage.size()> 0 && temps.size() > 0) {
				return true;
			}
		}
		return false;
	}
}