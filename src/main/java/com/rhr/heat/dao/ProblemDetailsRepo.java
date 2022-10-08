package com.rhr.heat.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.ProblemDetail;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemDetailsRepo {
	private final JdbcTemplate jdbcTemplate;

	public void saveAll(List<ProblemDetail> problems) {
		// TODO Auto-generated method stub
		
	}

	public void save(ProblemDetail pd1) {
		// TODO Auto-generated method stub
		
	}
}
