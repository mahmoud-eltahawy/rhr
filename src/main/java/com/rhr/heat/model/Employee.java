package com.rhr.heat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.rhr.heat.enums.EmployeePosition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	@Id @GeneratedValue
	@Column(name = "employee_id")
	private Long id;
	private String firstName;
	private String middleName;
	private String lastName;
	@Enumerated(EnumType.STRING)
	private EmployeePosition position;
}