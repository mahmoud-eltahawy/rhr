package com.rhr.heat.entity;


import java.util.Objects;
import java.util.UUID;

import com.rhr.heat.enums.EmployeePosition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	private UUID id;
	private String firstName;
	private String middleName;
	private String lastName;
	private EmployeePosition position;
	private String username;
	private String password;
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(username, other.username);
	}
	@Override
	public int hashCode() {
		return Objects.hash(username);
	}
}
