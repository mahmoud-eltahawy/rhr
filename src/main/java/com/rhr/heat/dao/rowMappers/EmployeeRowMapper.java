package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.enums.EmployeePosition;
import com.rhr.heat.model.Employee;

public class EmployeeRowMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Employee(rs.getLong("id"),
				rs.getString("first_name"),
				rs.getString("middle_name"),
				rs.getString("last_name"), 
				EmployeePosition.valueOf(rs.getString("emp_position")));
	}
}
