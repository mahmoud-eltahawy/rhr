package com.rhr.heat.entity.topLayer;

import java.util.List;

import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.Temperature;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MiniShift extends ShiftFamily {
	private List<Employee> employees;
	private List<Temperature> temps;
	private List<Note> notes;
	//TODO remove this constructor
	public MiniShift(ShiftId shiftId, List<Employee> employees, List<Temperature> temps, List<Note> notes) {
		super(shiftId);
		this.employees = employees;
		this.temps = temps;
		this.notes = notes;
	}
}
