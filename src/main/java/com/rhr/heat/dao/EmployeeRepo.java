package com.rhr.heat.dao;

import java.util.List;
import java.util.Optional;

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
	
	public Optional<Employee> findById(Long id) {
		String sql = "SELECT * FROM employee where id = ?";
		return jdbcTemplate.query(sql,
				new EmployeeRowMapper(), id).stream().findFirst();
	}
	
	public int delete(Employee employee) {
		String sql = "DELETE FROM employee where id =?";
		return jdbcTemplate.update(sql,(long) employee.hashCode());
	}

	public void saveAll(List<Employee> emps) {
		emps.forEach(e -> save(e) );
	}

	public Long save(Employee emp) {
		String sql = "INSERT INTO employee"
				+ "(id,first_name,middle_name,last_name,emp_position)"
				+ "VALUES(?,?,?,?,?)";
		jdbcTemplate.update(sql,
				(long) emp.hashCode(),
				emp.getFirstName(),
				emp.getMiddleName(),
				emp.getLastName(),
				emp.getPosition().toString());
		return (long) emp.hashCode();
	}
}
