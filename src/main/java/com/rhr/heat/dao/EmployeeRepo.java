package com.rhr.heat.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.EmployeeRowMapper;
import com.rhr.heat.dao.rowMappers.ShiftIdRowMapper;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.ShiftId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeRepo{
	private final JdbcTemplate jdbcTemplate;
	
	public List<Employee> findAll() {
		String sql = "SELECT * FROM employee";
		return jdbcTemplate.query(sql, new EmployeeRowMapper());
	}
	
	public List<String> findAllUserNames() {
		String sql = "SELECT username FROM employee";
		return jdbcTemplate.queryForList(sql, String.class);
	}
	
	public Optional<Employee> findById(UUID id) {
		String sql = "SELECT * FROM employee where id = ?";
		return jdbcTemplate.query(sql,
				new EmployeeRowMapper(), id).stream().findFirst();
	}
	
	public Optional<Employee> findByUsername(String username) {
		String sql = "SELECT * FROM employee where username = ?";
		return jdbcTemplate.query(sql,
				new EmployeeRowMapper(), username).stream().findFirst();
	}
	
	public List<ShiftId> findHisShifts(String username) {
		return jdbcTemplate.query("SELECT si.id,si.shift_date,si.shift_order "
				+ "FROM shift_id si "
				+ "JOIN shift s ON s.shift_id = si.id "
				+ "JOIN shift_employee se ON s.shift_id = se.shift_id "
				+ "JOIN employee e ON e.id = se.emp_id "
				+ "WHERE e.username = ? ORDER BY si.shift_date DESC", new ShiftIdRowMapper(), username);
	}
	
	public List<ShiftId> findhisShiftsOn(String username,Date older,Date newer) {
		return jdbcTemplate.query("SELECT si.id,si.shift_date,si.shift_order "
				+ "FROM shift_id si "
				+ "JOIN shift s ON s.shift_id = si.id "
				+ "JOIN shift_employee se ON s.shift_id = se.shift_id "
				+ "JOIN employee e ON e.id = se.emp_id "
				+ "WHERE e.username = ? and (si.shift_date >= ? "
				+ "and si.shift_date <= ?) ORDER BY si.shift_date DESC",
				new ShiftIdRowMapper(), username,older,newer);
	}
	
	public List<ShiftId> findHisLastShifts(String username,Integer set,Integer limit) {
		return jdbcTemplate.query("SELECT si.id,si.shift_date,si.shift_order "
				+ "FROM shift_id si "
				+ "JOIN shift s ON s.shift_id = si.id "
				+ "JOIN shift_employee se ON s.shift_id = se.shift_id "
				+ "JOIN employee e ON e.id = se.emp_id "
				+ "WHERE e.username = ? ORDER BY si.shift_date DESC OFFSET ? LIMIT ?",
				new ShiftIdRowMapper(), username, set, limit);
	}
	
	public int deleteById(UUID id) {
		String sql = "DELETE FROM employee where id =?";
		return jdbcTemplate.update(sql, id);
	}
	
	public int deleteByUsername(String username) {
		String sql = "DELETE FROM employee where id =?";
		return jdbcTemplate.update(sql, username);
	}

	public List<UUID> saveAll(List<Employee> emps) {
		return emps.stream()
				.map(e -> {return save(e);})
				.collect(Collectors.toList());
	}

	public UUID save(Employee emp) {
		Optional<Employee> e;
		if((e =findByUsername(emp.getUsername())).isPresent()) {
			return e.get().getId();
		} else {
			UUID uuid = UUID.randomUUID();
			jdbcTemplate.update("INSERT INTO employee "
					+ "(id,first_name,middle_name,last_name,emp_position,username,password) "
					+ "VALUES(?,?,?,?,?,?,?) ON CONFLICT(username) DO NOTHING",
					uuid,
					emp.getFirstName(),
					emp.getMiddleName(),
					emp.getLastName(),
					emp.getPosition().toString(),
					emp.getUsername(),
					emp.getPassword());
			return uuid;
		}
	}
}
