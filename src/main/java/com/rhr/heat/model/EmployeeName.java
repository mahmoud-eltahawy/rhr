package com.rhr.heat.model;

import java.util.UUID;

import com.rhr.heat.entity.Employee;

import lombok.Data;

@Data
public class EmployeeName {
	private final UUID id;
	private final String name;
	
	public EmployeeName(Employee employee) {
		this.id = employee.getId();
		this.name = employee.getFirstName()
				+" "+employee.getMiddleName()
				+" "+employee.getLastName();
	}
}
