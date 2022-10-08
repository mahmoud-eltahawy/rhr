package com.rhr.heat.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.TotalFlow;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TotalFlowRepo {
	private final JdbcTemplate jdbcTemplate;

	public void saveAll(List<TotalFlow> totalFlowAverage) {
		// TODO Auto-generated method stub
		
	}

	public void save(TotalFlow tf2) {
		// TODO Auto-generated method stub
		
	}
}
