package com.rhr.heat.entity;


import com.rhr.heat.enums.EmployeePosition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	private Long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private EmployeePosition position;
	private String username;
	private String password;
}