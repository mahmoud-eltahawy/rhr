package com.rhr.heat.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	
	public Optional<Employee> findByUsername(String username) {
		String sql = "SELECT * FROM employee where id = ?";
		return jdbcTemplate.query(sql,
				new EmployeeRowMapper(), username).stream().findFirst();
	}
	
	public int deleteById(Long id) {
		String sql = "DELETE FROM employee where id =?";
		return jdbcTemplate.update(sql, id);
	}
	
	public int deleteByUsername(String username) {
		String sql = "DELETE FROM employee where id =?";
		return jdbcTemplate.update(sql, username);
	}

	public List<Long> saveAll(List<Employee> emps) {
		return emps.stream()
				.map(e -> {return save(e);})
				.collect(Collectors.toList());
	}

	public Long save(Employee emp) {
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(connection ->{
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO employee "
				+ "(first_name,middle_name,last_name,emp_position) "
				+ "VALUES(?,?,?,?,?,?) ON CONFLICT(username) DO NOTHING", 
							Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, emp.getFirstName());
			ps.setString(2, emp.getMiddleName());
			ps.setString(3, emp.getLastName());
			ps.setString(4, emp.getPosition().toString());
			ps.setString(5, emp.getUsername());
			ps.setString(6, emp.getPassword());
			return ps;
		},key);
	 if (key.getKeys().size() > 1) {
			return (Long)key.getKeys().get("id");
		} else {
			return key.getKey().longValue();
		}
	}
}
