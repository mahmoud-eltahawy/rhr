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
	
	public List<Employee> findInShift(UUID id){
		String sql = "SELECT e.* FROM employee e "
				+ "join shift_employee se on e.id = se.employee_id "
				+ "and where se.shift_id =?";
		return jdbcTemplate.query(sql,
				new EmployeeRowMapper(), id);
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
		return jdbcTemplate.query(
				"SELECT s.id,s.shift_date,s.shift_order "
				+ "FROM shift s "
				+ "JOIN shift_employee se ON s.id = se.shift_id "
				+ "JOIN employee e ON e.id = se.employee_id "
				+ "WHERE e.username = ? "
				+ "ORDER BY s.shift_date DESC",
				new ShiftIdRowMapper(), username);
	}
	
	public List<ShiftId> findhisShiftsOn(String username,Date older,Date newer) {
		return jdbcTemplate.query(
				"SELECT s.id,s.shift_date,s.shift_order "
				+ "FROM shift s "
				+ "JOIN shift_employee se ON s.id = se.shift_id "
				+ "JOIN employee e ON e.id = se.employee_id "
				+ "WHERE e.username = ? "
				+ "and (s.shift_date >= ? and s.shift_date <= ?) "
				+ "ORDER BY s.shift_date DESC",
				new ShiftIdRowMapper(), username,older,newer);
	}
	
	public List<ShiftId> findHisLastShifts(String username,Integer set,Integer limit) {
		return jdbcTemplate.query(
				"SELECT s.id,s.shift_date,s.shift_order "
				+ "FROM shift s "
				+ "JOIN shift_employee se ON s.id = se.shift_id "
				+ "JOIN employee e ON e.id = se.employee_id "
				+ "WHERE e.username = ? "
				+ "ORDER BY s.shift_date DESC OFFSET ? LIMIT ?",
				new ShiftIdRowMapper(), username, set, limit);
	}
	
	public int deleteById(UUID id) {
		String sql = "DELETE FROM employee where id =?";
		return jdbcTemplate.update(sql, id);
	}
	
	public int deleteByUsername(String username) {
		String sql = "DELETE FROM employee where username =?";
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
	
	public void saveToShift(Employee emp,UUID empId) {
		UUID eId = null;
		if(emp.getId() != null) {
			eId = emp.getId();
		} else {
			eId = save(emp);
		}
		jdbcTemplate.update("INSERT INTO shift_employee"
				+ "(shift_id,employee_id) VALUES(?,?)",empId,eId);
	}
}