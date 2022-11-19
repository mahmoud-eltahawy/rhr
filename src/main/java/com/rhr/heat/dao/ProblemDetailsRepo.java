package com.rhr.heat.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.ProblemDetailRowMapper;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.enums.Pushable;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemDetailsRepo {
	private final JdbcTemplate jdbcTemplate;
	private final ProblemRepo  problemRepo;
	private final MachineRepo machineRepo;
	
	public Optional<ProblemDetail> findById(UUID id) {
		Optional<ProblemDetail> pd  = jdbcTemplate.query(
				"SELECT * FROM problem_detail WHERE id = ?", 
				new ProblemDetailRowMapper(),id)
				.stream().findFirst();
		if(pd.isPresent()) {
			return Optional.of(fullFill(pd.get()));
		} else {
			return pd;
		}
	}

	public List<ProblemDetail> findByShiftId(UUID id){
		return jdbcTemplate.query("""
				SELECT pd.* FROM problem_detail pd
				where pd.shift_id = ?
				""", new ProblemDetailRowMapper(),id)
				.stream().map(pd -> fullFill(pd)).collect(Collectors.toList());
	}

	public List<ProblemDetail> findByMachineId(UUID id){
		return jdbcTemplate.query("""
				SELECT pd.* FROM problem_detail pd
				where pd.machine_id = ?
				""", new ProblemDetailRowMapper(),id)
				.stream().map(pd -> fullFill(pd)).collect(Collectors.toList());
	}
	
	public List<ProblemDetail> findAll(){
		return jdbcTemplate.query(
				"SELECT * FROM problem_detail",
				new ProblemDetailRowMapper())
				.stream().map(pd -> fullFill(pd))
				.collect(Collectors.toList());
	}
	
	public int deleteById(UUID id) {
		return jdbcTemplate.update("DELETE FROM problem_detail WHERE id = ?",id);
	}

	public List<Pushable> saveAll(List<ProblemDetail> problems) {
		List<Pushable> result = new ArrayList<>();
		for (ProblemDetail problemDetail : problems) {
			result.addAll(save(problemDetail));
		}
		return result;
	}

	public List<Pushable> save(ProblemDetail pd) {
		List<Pushable> result = pd.isPushable();
		if(result.isEmpty()) {
			jdbcTemplate.update("""
					INSERT INTO problem_detail(id,shift_id,machine_id,
					begin_time, end_time) VALUES(?,?,?,?,?)
					""",
						pd.getId(),
						pd.getShiftId(),
						pd.getMachine().getId(),
						pd.getBeginTime(),
						pd.getEndTime());
			
			pd.getProblems().forEach(p ->problemRepo
					.saveToProblemDetail(p.getTitle(), pd.getId()));
		} 
		return result;
	}
	
	public ProblemDetail fullFill(ProblemDetail pd) {
		pd.setMachine(machineRepo.findById(pd.getMachine().getId()).orElseThrow());
		pd.setProblems(problemRepo.findProblemDetailProblems(pd.getId()));
		return pd;
	}
}