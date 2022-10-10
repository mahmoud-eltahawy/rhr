package com.rhr.heat.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.ProblemDetail;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemDetailsRepo {
	private final JdbcTemplate jdbcTemplate;
	
	public List<ProblemDetail> findAll(){
		return null;
	}
	
	public Optional<ProblemDetail> findById(Long id) {
		return null;
	}

	public void saveAll(List<ProblemDetail> problems) {
		problems.forEach(p -> save(p));
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