package com.rhr.heat.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.EmployeeRowMapper;
import com.rhr.heat.model.Employee;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeRepo{
	private final JdbcTemplate jdbcTemplate;
	
	public List<Employee> findAll() {
		String sql = "SELECT * FROM employee";
		return jdbcTemplate.query(sql, new EmployeeRowMapper());
	}

	public void saveAll(List<Employee> emps) {
		// TODO Auto-generated method stub
		
	}

	public void save(Employee emp) {
		String sql = "INSERT INTO employee"
				+ "(first_name,middle_name,last_name,emp_position)"
				+ "VALUES(?,?,?,?)";
		jdbcTemplate.update(sql,emp.getFirstName(),emp.getMiddleName()
				,emp.getLastName(),emp.getPosition().toString());
	}
}
