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
import com.rhr.heat.model.ProblemDetail;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemDetailsRepo {
	private final JdbcTemplate jdbcTemplate;
	
	public List<ProblemDetail> findAll(){
		String sql = "SELECT * FROM problem_detail";
		return jdbcTemplate.query(sql, new ProblemDetailRowMapper());
	}
	
	public Optional<ProblemDetail> findById(Long id) {
		String sql = "SELECT * FROM problem_detail WHERE id = ?";
		return jdbcTemplate.query(sql, 
				new ProblemDetailRowMapper(),id)
				.stream().findFirst();
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
					.prepareStatement("INSERT INTO problem_detail(problem, machine,"
							+ "begin_time, end_time) VALUES(?,?,?,?)", 
							Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pd.getProblem().toString());
			ps.setString(2, pd.getMachine().toString());
			ps.setTime(3, pd.getBeginTime());
			ps.setTime(4, pd.getEndTime());
			return ps;
		},key);
	 if (key.getKeys().size() > 1) {
			return (Long)key.getKeys().get("id");
		} else {
			return key.getKey().longValue();
		}
	}
}