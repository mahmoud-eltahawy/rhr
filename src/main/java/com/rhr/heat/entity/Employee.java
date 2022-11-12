package com.rhr.heat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.rhr.heat.enums.EmployeePosition;
import com.rhr.heat.enums.Pushable;

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
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(firstName == null) {
			canPush.add(Pushable.EMPLOYEE_FIRST_NAME_NULL);
		} 
		if(middleName == null) {
			canPush.add(Pushable.EMPLOYEE_MIDDLE_NAME_NULL);
		}
		if(lastName == null) {
			canPush.add(Pushable.EMPLOYEE_LAST_NAME_NULL);
		}
		if(position == null) {
			canPush.add(Pushable.EMPLOYEE_POSITION_NULL);
		}
		if(username == null) {
			canPush.add(Pushable.EMPLOYEE_USERNAME_NULL);
		}
		if(password == null) {
			canPush.add(Pushable.EMPLOYEE_PASSWORD_NULL);
		}
		return canPush;
	}
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
