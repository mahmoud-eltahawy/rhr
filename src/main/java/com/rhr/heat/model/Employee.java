package com.rhr.heat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	@Id @GeneratedValue
	private Long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private EmployeePosition position;
}