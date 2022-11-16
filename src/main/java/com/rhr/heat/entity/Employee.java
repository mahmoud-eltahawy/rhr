package com.rhr.heat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.rhr.heat.enums.EmployeePosition;
import com.rhr.heat.enums.Pushable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee extends Identity {
	private String firstName;
	private String middleName;
	private String lastName;
	private EmployeePosition position;
	private String username;
	private String password;

	public Employee(UUID id) {
		super(id);
	}
	
	public Employee(UUID id, String firstName, String middleName, String lastName, EmployeePosition position,
			String username, String password) {
		super(id);
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.position = position;
		this.username = username;
		this.password = password;
	}
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(id == null) {
			canPush.add(Pushable.EMPLOYEE_ID_NULL);
		} 
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
}
