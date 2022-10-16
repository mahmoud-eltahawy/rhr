package com.rhr.heat.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.ProblemDetailRowMapper;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.enums.Problem;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemDetailsRepo {
	private final JdbcTemplate jdbcTemplate;
	
	public List<ProblemDetail> findAll(){
		String sql = "SELECT * FROM problem_detail";
		return jdbcTemplate.query(sql, new ProblemDetailRowMapper())
				.stream().map(pd -> fullFill(pd)).collect(Collectors.toList());
	}
	
	public Optional<ProblemDetail> findById(Long id) {
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
		jdbcTemplate.update("INSERT INTO problem_detail(id,machine,"
							+ "begin_time, end_time) VALUES(?,?,?,?)",
							uuid,
							pd.getMachine().toString(),
							pd.getBeginTime(),
							pd.getEndTime());
		
		pd.getProblems().forEach(m ->{
			jdbcTemplate.update("INSERT INTO "
					+ "problems(id,problem) values(?,?) "
					+ "ON CONFLICT(id,problem) DO NOTHING",uuid,m.toString());
		});
		return uuid;
	}
	
	private ProblemDetail fullFill(ProblemDetail pd) {
		pd.setProblems(jdbcTemplate.queryForList("SELECT problem FROM "
				+ "problems where id =?",String.class,pd.getId())
				.stream().map(s -> Problem.valueOf(s)).collect(Collectors.toSet()));
		return pd;
	}
}