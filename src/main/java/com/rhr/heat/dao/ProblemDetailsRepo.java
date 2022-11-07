package com.rhr.heat.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.ProblemDetailRowMapper;
import com.rhr.heat.entity.ProblemDetail;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemDetailsRepo {
	private final JdbcTemplate jdbcTemplate;
	private final ProblemRepo  problemRepo;
	private final MachineRepo machineRepo;
	
	public List<ProblemDetail> findAll(){
		String sql = "SELECT * FROM problem_detail";
		return jdbcTemplate.query(sql, new ProblemDetailRowMapper())
				.stream().map(pd -> fullFill(pd)).collect(Collectors.toList());
	}
	
	public List<ProblemDetail> findInShift(UUID id){
		String sql = "SELECT pd.* FROM problem_detail pd "
				+ "join shift_problem sp on sp.problem_detail_id = pd.id "
				+ "where sp.shift_id = ?";
		return jdbcTemplate.query(sql, new ProblemDetailRowMapper())
				.stream().map(pd -> fullFill(pd)).collect(Collectors.toList());
	}
	
	public Optional<ProblemDetail> findById(UUID id) {
		String sql = "SELECT * FROM problem_detail WHERE id = ?";
		Optional<ProblemDetail> pd  = jdbcTemplate.query(sql, 
				new ProblemDetailRowMapper(),id)
				.stream().findFirst();
		if(pd.isPresent()) {
			return Optional.of(fullFill(pd.get()));
		} else {
			return pd;
		}
	}
	
	public int deleteById(UUID id) {
		String sql = "DELETE FROM problem_detail WHERE id = ?";
		return jdbcTemplate.update(sql,id);
	}

	public List<UUID> saveAll(List<ProblemDetail> problems) {
		return problems.stream()
				.map(p -> {return save(p);})
				.collect(Collectors.toList());
	}

	public UUID save(ProblemDetail pd) {
		UUID uuid = UUID.randomUUID();
		jdbcTemplate.update("INSERT INTO problem_detail(id,machine_id,"
							+ "begin_time, end_time) VALUES(?,?,?,?)",
							uuid,
							pd.getMachine().getId(),
							pd.getBeginTime(),
							pd.getEndTime());
		
		pd.getProblems().forEach(m ->{
			jdbcTemplate.update("INSERT INTO problem_detail_problem"
					+ "(problem_detail_id,problem_title) values(?,?) ",
					uuid,m.getTitle());
		});
		return uuid;
	}

	public void saveToShift(ProblemDetail pd,UUID shiftId) {
		UUID pdId = null;
		if(pd.getId() != null) {
			pdId = pd.getId();
		} else {
			pdId = save(pd);
		}
		jdbcTemplate.update("INSERT INTO shift_problem"
				+ "(shift_id,problem_id) VALUES(?,?)",shiftId,pdId);
	}
	
	public ProblemDetail fullFill(ProblemDetail pd) {
		pd.setMachine(machineRepo.findById(pd.getMachine().getId()).orElseThrow());
		pd.setProblems(problemRepo.findProblemDetailProblems(pd.getId()));
		return pd;
	}
}