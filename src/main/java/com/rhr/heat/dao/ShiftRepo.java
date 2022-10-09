package com.rhr.heat.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.Shift;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShiftRepo {
	private final JdbcTemplate jdbcTemplate;

	public List<Shift> findAll() {
		
		return null;
	}

	public void saveAll(List<Shift> shifts) {
		shifts.forEach(s ->save(s));
	}

	public void save(Shift shift) {
		String mainSql      = "INSERT INTO shift(id,min_temp,max_temp,notes)  VALUES(?,?,?,?)";
		String problemSql   = "INSERT INTO shift_problem(shift_id,problem_id) VALUES(?,?)";
		String totalFlowSql = "INSERT INTO shift_total_flow(shift_id,flow_id) VALUES(?,?)";
		String employeeSql  = "INSERT INTO shift_employee(shift_id,emp_id)    VALUES(?,?)";
		
		Long shiftId = (long) shift.hashCode();
		jdbcTemplate.update(mainSql,
				shiftId,
				shift.getMinTemperature(),
				shift.getMaxTemperature(),
				shift.getExceptionalNote());
		
		shift.getProblems().forEach(p -> {
			jdbcTemplate.update(problemSql,shiftId,(long) p.hashCode());
		});
		
		shift.getTotalFlowAverage().forEach(t -> {
			jdbcTemplate.update(totalFlowSql,shiftId,(long) t.hashCode());
		});
		
		shift.getEmployees().forEach(e -> {
			jdbcTemplate.update(employeeSql,shiftId,(long) e.hashCode());
		});
	}
}
