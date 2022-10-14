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
	
	public int deleteById(Long id) {
		String sql = "DELETE FROM problem_detail WHERE id = ?";
		return jdbcTemplate.update(sql,id);
	}

	public List<Long> saveAll(List<ProblemDetail> problems) {
		return problems.stream()
				.map(p -> {return save(p);})
				.collect(Collectors.toList());
	}

	public Long save(ProblemDetail pd) {
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(connection ->{
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO problem_detail(machine,"
							+ "begin_time, end_time) VALUES(?,?,?)", 
							Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pd.getMachine().toString());
			ps.setTime(2, pd.getBeginTime());
			ps.setTime(3, pd.getEndTime());
			return ps;
		},key);
		
		final Long id;
		if (key.getKeys().size() > 1) {
			id = (Long)key.getKeys().get("id");
		} else {
			id = key.getKey().longValue();
		}
		pd.getProblems().forEach(m ->{
			jdbcTemplate.update("INSERT INTO "
					+ "problems(id,problem) values(?,?) "
					+ "ON CONFLICT(id,problem) DO NOTHING",id,m.toString());
		});
		return id;
	}
	
	private ProblemDetail fullFill(ProblemDetail pd) {
		pd.setProblems(jdbcTemplate.queryForList("SELECT problem FROM "
				+ "problems where id =?",String.class,pd.getId())
				.stream().map(s -> Problem.valueOf(s)).collect(Collectors.toSet()));
		return pd;
	}
}