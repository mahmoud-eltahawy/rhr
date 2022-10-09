package com.rhr.heat.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
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
	
	public void saveAll(List<ProblemDetail> problems) {
		problems.forEach(p -> save(p));
	}

	public Long save(ProblemDetail pd) {
		String sql = "INSERT INTO problem_detail(id,problem,machine,"
				+ "begin_hour,begin_minute,end_hour,end_minute) "
				+ "VALUES(?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,
				pd.hashCode(),
				pd.getProblem().toString(),
				pd.getMachine().toString(),
				pd.getBeginTime().getHour(),
				pd.getBeginTime().getMinute(),
				pd.getEndTime().getHour(),
				pd.getEndTime().getMinute());
		return (long) pd.hashCode();
	}
}