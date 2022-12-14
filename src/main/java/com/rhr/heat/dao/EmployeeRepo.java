package com.rhr.heat.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.EmployeeRowMapper;
import com.rhr.heat.dao.rowMappers.ShiftIdRowMapper;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.enums.Pushable;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeRepo{
	private final JdbcTemplate jdbcTemplate;

	public int saveToShift(UUID empId,UUID shiftId) {
		return jdbcTemplate.update("""
				INSERT INTO shift_employee
				(shift_id,employee_id) VALUES(?,?)
				ON CONFLICT(shift_id,employee_id) DO NOTHING
				""",shiftId,empId);
	}

	public void deleteFromShift(UUID empId,UUID shiftId) {
		jdbcTemplate.update("""
				DELETE FROM shift_employee 
				WHERE shift_id =? AND employee_id =?
				""",shiftId,empId);
	}

	public int removeFromShift(UUID empId,UUID shiftId) {
		return jdbcTemplate.update("""
				DELETE FROM shift_employee
				WHERE shift_id =? AND employee_id =?
				""",shiftId,empId);
	}

	public int removeAllFromShift(UUID shiftId) {
		return jdbcTemplate
		.update("DELETE FROM shift_employee WHERE shift_id =?",
		shiftId);
	}
	
	public List<Employee> findByShiftId(UUID id){
		return jdbcTemplate.query("""
				SELECT e.* FROM employee e 
				JOIN shift_employee se ON e.id = se.employee_id
				WHERE se.shift_id =?
				""",
				new EmployeeRowMapper(), id);
	}
	
	public List<Employee> findAll() {
		return jdbcTemplate.query("SELECT * FROM employee"
				, new EmployeeRowMapper());
	}
	
	
	public List<String> findAllUserNames() {
		return jdbcTemplate.queryForList(
				"SELECT e.username FROM employee e", String.class);
	}
	
	public Optional<Employee> findById(UUID id) {
		return jdbcTemplate.query("SELECT e.* FROM employee e WHERE e.id = ?",
				new EmployeeRowMapper(), id).stream().findFirst();
	}
	
	public Optional<Employee> findByUsername(String username) {
		return jdbcTemplate.query("SELECT e.* FROM employee e where e.username = ?",
				new EmployeeRowMapper(), username).stream().findFirst();
	}
	
	public List<ShiftId> findHisShifts(String username) {
		return jdbcTemplate.query("""
				SELECT s.* FROM shift s
				JOIN shift_employee se ON s.id = se.shift_id
				JOIN employee e ON e.id = se.employee_id
				WHERE e.username = ?
				ORDER BY s.shift_date DESC
				""",
				new ShiftIdRowMapper(), username);
	}
	
	public List<ShiftId> findhisShiftsOn(String username,Date older,Date newer) {
		return jdbcTemplate.query("""
				SELECT s.* FROM shift s
				JOIN shift_employee se ON s.id = se.shift_id
				JOIN employee e ON e.id = se.employee_id
				WHERE e.username = ?
				and (s.shift_date >= ? and s.shift_date <= ?)
				ORDER BY s.shift_date DESC
				""",
				new ShiftIdRowMapper(), username,older,newer);
	}
	
	public List<ShiftId> findHisLastShifts(String username,Integer set,Integer limit) {
		return jdbcTemplate.query("""
				SELECT s.* FROM shift s
				JOIN shift_employee se ON s.id = se.shift_id
				JOIN employee e ON e.id = se.employee_id
				WHERE e.username = ?
				ORDER BY s.shift_date DESC OFFSET ? LIMIT ?
				""",
				new ShiftIdRowMapper(), username, set, limit);
	}
	
	public int deleteById(UUID id) {
		return jdbcTemplate.update(
				"DELETE FROM employee WHERE id =?", id);
	}
	
	public int deleteByUsername(String username) {
		return jdbcTemplate.update(
				"DELETE FROM employee WHERE username =?", username);
	}

	public List<Pushable> saveAll(List<Employee> emps) {
		List<Pushable> result = new ArrayList<>();
		for (Employee employee : emps) {
			 result.addAll(save(employee));
		}
		return result;
	}

	public List<Pushable> save(Employee emp) {
		List<Pushable> result = emp.isPushable();
		if(result.isEmpty()) {
			jdbcTemplate.update("""
					INSERT INTO employee
					(id,first_name,middle_name,last_name,emp_position,username,password)
					VALUES(?,?,?,?,?,?,?) ON CONFLICT(username) DO NOTHING
					""",
					emp.getId(),
					emp.getFirstName(),
					emp.getMiddleName(),
					emp.getLastName(),
					emp.getPosition().toString(),
					emp.getUsername(),
					emp.getPassword());
		}
		return result;
	}
}