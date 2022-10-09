package com.rhr.heat.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.model.ShiftId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShiftIdRepo {
	private final JdbcTemplate jdbcTemplate;

	public Long save(ShiftId shiftId) {
		String sql = "INSERT INTO shift_id"
				+ "(id,shift_order,my_year,my_month,my_day) "
				+ "VALUES(?,?,?,?)";
		Long id = (long) shiftId.hashCode();
		jdbcTemplate.update(sql,
				id,
				shiftId.getShift().toString(),
				shiftId.getDate().getYear(),
				shiftId.getDate().getMonth(),
				shiftId.getDate().getDay());
		
		return id;
	}
}
