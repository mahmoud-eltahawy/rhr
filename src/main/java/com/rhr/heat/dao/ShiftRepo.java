package com.rhr.heat.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.Shift;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShiftRepo {
	private final JdbcTemplate jdbcTemplate;
	private final EmployeeRepo employeeRepo;
	private final ProblemDetailsRepo problemDetailsRepo;
	private final ShiftIdRepo shiftIdRepo;
	private final TotalFlowRepo totalFlowRepo;

	public List<Shift> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Long> saveAll(List<Shift> shifts) {
		return shifts.stream()
				.map(s -> {return save(s);})
				.collect(Collectors.toList());
	}

	public Long save(Shift s) {
		Long theId = shiftIdRepo.save(s.getShiftId());
		if(theId == null) {
			return theId;
		} else {
			jdbcTemplate.update("INSERT INTO shift"
					+ "(shift_id,max_temp,min_temp,notes)"
					+ " VALUES(?,?,?,?)",
					theId,s.getMaxTemperature(),
					s.getMinTemperature(),
					s.getExceptionalNote());
			
			s.getProblems().forEach(p -> {
				Long pId = null;
				if(p.getId() != null) {
					pId = p.getId();
				} else {
					pId = problemDetailsRepo.save(p);
				}
				jdbcTemplate.update("INSERT INTO shift_problem"
						+ "(shift_id,problem_id) VALUES(?,?)",theId,pId);
			});
			
			s.getEmployees().forEach(e ->{
				Long eId = null;
				if(e.getId() != null) {
					eId = e.getId();
				} else {
					eId = employeeRepo.save(e);
				}
				jdbcTemplate.update("INSERT INTO shift_employee"
						+ "(shift_id,emp_id) VALUES(?,?)",theId,eId);
			});
			
			s.getTotalFlowAverage().forEach(t ->{
				Long tId = null;
				if(t.getId() != null) {
					tId = t.getId();
				} else {
					tId = totalFlowRepo.save(t);
				}
				jdbcTemplate.update("INSERT INTO shift_total_flow"
						+ "(shift_id,flow_id) VALUES(?,?)",theId,tId);
			});
			return theId;
		}
	}
}
