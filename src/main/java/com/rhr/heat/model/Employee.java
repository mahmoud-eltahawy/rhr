package com.rhr.heat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	private String firstName;
	private String middleName;
	private String lastName;
	private EmployeePosition position;
}