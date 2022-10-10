package com.rhr.heat.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
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

	public void save(ProblemDetail pd) {
		String sql = "INSERT INTO problem_detail(problem, machine,"
				+ "begin_time, end_time) VALUES(?,?,?,?)";
		jdbcTemplate.update(sql,
				pd.getProblem().toString(),
				pd.getMachine().toString(),
				pd.getBeginTime().form(),
				pd.getBeginTime().form());
	}
}