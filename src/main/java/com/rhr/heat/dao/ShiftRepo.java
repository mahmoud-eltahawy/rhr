package com.rhr.heat.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.Shift;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShiftRepo {
	private final JdbcTemplate jdbcTemplate;

	public List<Shift> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveAll(List<Shift> shifts) {
		// TODO Auto-generated method stub
		
	}

	public void save(Shift shift3) {
		// TODO Auto-generated method stub
		
	}
}
