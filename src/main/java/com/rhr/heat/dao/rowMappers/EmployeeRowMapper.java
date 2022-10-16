package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.entity.Employee;
import com.rhr.heat.enums.EmployeePosition;

public class EmployeeRowMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Employee((UUID) rs.getObject("id"),
				rs.getString("first_name"),
				rs.getString("middle_name"),
				rs.getString("last_name"), 
				EmployeePosition.valueOf(rs.getString("emp_position")),
				rs.getString("username"),
				rs.getString("password"));
	}
}
