package com.rhr.heat.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.EmployeeRowMapper;
import com.rhr.heat.dao.rowMappers.ProblemDetailRowMapper;
import com.rhr.heat.dao.rowMappers.ShiftRowMapper;
import com.rhr.heat.dao.rowMappers.TotalFlowRowMapper;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.enums.Machine;
import com.rhr.heat.enums.Problem;
import com.rhr.heat.enums.ShiftOrder;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShiftRepo {
	private final JdbcTemplate jdbcTemplate;
	private final EmployeeRepo employeeRepo;
	private final ProblemDetailsRepo problemDetailsRepo;
	private final ShiftIdRepo shiftIdRepo;
	private final TotalFlowRepo totalFlowRepo;

	public List<Shift> findAll(Boolean perfect) {
		 List<Shift> shifts = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id",
				new ShiftRowMapper());
		 if(perfect) {
			 shifts = shifts.stream()
					 .map(s ->{return fullFill(s);})
					 .collect(Collectors.toList());
		 }
		 return shifts;
	}
	
	public List<Shift> findAll(Date date,Boolean perfect) {
		 List<Shift> shifts = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id "
				+ "where si.shift_date = ?",
				new ShiftRowMapper(), date);
		 if(perfect) {
			 shifts = shifts.stream()
					 .map(s ->{return fullFill(s);})
					 .collect(Collectors.toList());
		 }
		 return shifts;
	}

	public List<Shift> findAll(ShiftOrder order,Boolean perfect) {
		 List<Shift> shifts = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id "
				+ "where si.shift_order = ?",
				new ShiftRowMapper(), order.toString());
		 if(perfect) {
			 shifts = shifts.stream()
					 .map(s ->{return fullFill(s);})
					 .collect(Collectors.toList());
		 }
		 return shifts;
	}

	public Optional<Shift> findById(Long id,Boolean perfect) {
		 Optional<Shift> s = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id "
				+ "where si.id = ?",
				new ShiftRowMapper(),id).stream().findFirst();
		 if(s.isPresent() & perfect) {
			 return Optional.of(fullFill(s.get()));
		 } else {
			 return s;
		 }
	}

	public Optional<Shift> findById(Date date, ShiftOrder order,Boolean perfect) {
		 Optional<Shift> s = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id "
				+ "where si.shift_date = ? and si.shift_order = ?",
				new ShiftRowMapper(),date,order.toString()).stream().findFirst();
		 if(s.isPresent() & perfect) {
			 return Optional.of(fullFill(s.get()));
		 } else {
			 return s;
		 }
	}

	public List<Shift> findOlderThan(Date date,Boolean perfect) {
		 List<Shift> shifts = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id "
				+ "where si.shift_date <= ?",
				new ShiftRowMapper(),date);
		 if(perfect) {
			 shifts = shifts.stream()
					 .map(s ->{return fullFill(s);})
					 .collect(Collectors.toList());
		 }
		 return shifts;
	}

	public List<Shift> findOlderThan(Date date,ShiftOrder order,Boolean perfect) {
		 List<Shift> shifts = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id "
				+ "where si.shift_date <= ? and si.shift_order = ?",
				new ShiftRowMapper(),date ,order.toString());
		 if(perfect) {
			 shifts = shifts.stream()
					 .map(s ->{return fullFill(s);})
					 .collect(Collectors.toList());
		 }
		 return shifts;
	}

	public List<Shift> findRecent(Date date,Boolean perfect) {
		 List<Shift> shifts = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id "
				+ "where si.shift_date >= ?",
				new ShiftRowMapper(),date);
		 if(perfect) {
			 shifts = shifts.stream()
					 .map(s ->{return fullFill(s);})
					 .collect(Collectors.toList());
		 }
		 return shifts;
	}

	public List<Shift> findRecent(Date date, ShiftOrder order,Boolean perfect) {
		 List<Shift> shifts = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id "
				+ "where si.shift_date >= ? and si.shift_order = ?",
				new ShiftRowMapper(),date ,order.toString());
		 if(perfect) {
			 shifts = shifts.stream()
					 .map(s ->{return fullFill(s);})
					 .collect(Collectors.toList());
		 }
		 return shifts;
	}

	public List<Shift> findLast(Integer num,Boolean perfect) {
		 List<Shift> shifts = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id "
				+ " order by si.shift_date desc limit ?",
				new ShiftRowMapper(), num);
		 if(perfect) {
			 shifts = shifts.stream()
					 .map(s ->{return fullFill(s);})
					 .collect(Collectors.toList());
		 }
		 return shifts;
	}

	public List<Shift> findFromTo(Integer from,Integer to,Boolean perfect) {
		 List<Shift> shifts = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id "
				+ " order by si.shift_date desc offset ? limit ?",
				new ShiftRowMapper(), from, to);
		 if(perfect) {
			 shifts = shifts.stream()
					 .map(s ->{return fullFill(s);})
					 .collect(Collectors.toList());
		 }
		 return shifts;
	}

	public List<Shift> findBetween(Date older,Date newer,Boolean perfect) {
		 List<Shift> shifts = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id "
				+ "where si.shift_date between ? and ?",
				new ShiftRowMapper(),older,newer);
		 if(perfect) {
			 shifts = shifts.stream()
					 .map(s -> {return fullFill(s);})
					 .collect(Collectors.toList());
		 }
		 return shifts;
	}

	public List<Shift> findBetween(Date older, Date newer, ShiftOrder order,Boolean perfect) {
		 List<Shift> shifts = jdbcTemplate.query(
				 "select si.id as shift_id, si.shift_order,"
				+ "si.shift_date, s.max_temp, s.min_temp, s.notes "
				+ "from shift s join shift_id si on s.shift_id = si.id "
				+ "where si.shift_date between ? and ? and si.shift_order = ?",
				new ShiftRowMapper(),older,newer,order.toString());
		 if(perfect) {
			 shifts = shifts.stream()
					 .map(s -> {return fullFill(s);})
					 .collect(Collectors.toList());
		 }
		 return shifts;
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
	
	private Shift fullFill(Shift s) {
		 s.setEmployees(jdbcTemplate.query(
				 "SELECT e.id,e.first_name,e.middle_name,e.last_name,e.username,"
				 + "e.emp_position,e.password FROM employee e JOIN shift_employee "
				 + "se ON e.id = se.emp_id JOIN shift s ON se.shift_id = s.shift_id "
				 + "where s.shift_id = ?",
			 new EmployeeRowMapper(),s.getShiftId().getId()));
		 s.setProblems(jdbcTemplate.query(
				 "SELECT pd.id, pd.machine, pd.begin_time,"
				 +"pd.end_time FROM problem_detail pd JOIN shift_problem "
				 +"sp ON pd.id = sp.problem_id JOIN shift s ON sp.shift_id = s.shift_id "
				 +"where s.shift_id = ?", 
				 new ProblemDetailRowMapper(),s.getShiftId().getId()).stream().map(pd ->{
					pd.setProblems(jdbcTemplate.queryForList("SELECT problem FROM "
							+ "problems where id =?",String.class,pd.getId())
							.stream().map(m -> Problem.valueOf(m)).collect(Collectors.toSet()));
					 return pd;
				 }).collect(Collectors.toList()));
		 s.setTotalFlowAverage(jdbcTemplate.query(
				 "SELECT tf.id, tf.begin_time, tf.end_time,"
				 +"tf.min_flow, tf.max_flow FROM total_flow tf JOIN shift_total_flow "
				 +"sf ON tf.id = sf.flow_id JOIN shift s ON sf.shift_id = s.shift_id "
				 +"where s.shift_id = ?", 
				 new TotalFlowRowMapper(), s.getShiftId().getId()).stream().map(tf -> {
					tf.setSuspendedMachines(jdbcTemplate.queryForList("SELECT machine FROM "
							+ "suspended_machine where id =?",String.class,tf.getId())
							.stream().map(m -> Machine.valueOf(m)).collect(Collectors.toList()));
					 return tf;
				 }).collect(Collectors.toList()));
		 return s;
	}
}
