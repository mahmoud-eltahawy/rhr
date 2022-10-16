package com.rhr.heat.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.ShiftIdRowMapper;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.enums.ShiftOrder;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShiftIdRepo {
	private final JdbcTemplate jdbcTemplate;
	
	public List<ShiftId> findAll() {
		String sql = "SELECT * FROM shift_id";
		return jdbcTemplate.query(sql, new ShiftIdRowMapper());
	}
	
	public Optional<ShiftId> findById(UUID id) {
		String sql = "SELECT * FROM shift_id WHERE id = ?";
		return jdbcTemplate.query(sql,
				new ShiftIdRowMapper(), id).stream().findFirst();
	}
	
	public Optional<ShiftId> findById(Date date,ShiftOrder order) {
		String sql = "SELECT si.* FROM shift_id si WHERE si.shift_date = ? and si.shift_order = ?";
		return jdbcTemplate.query(sql,
				new ShiftIdRowMapper(), date,order.toString()).stream().findFirst();
	}
	
	public int deleteById(UUID id) {
		String sql = "DELETE FROM shift_id WHERE id =?";
		return jdbcTemplate.update(sql, id);
	}

	public List<UUID> saveAll(List<ShiftId> ids) {
		return ids.stream()
				.map(i -> {return save(i);})
				.collect(Collectors.toList());
	}

	public UUID save(ShiftId id) {
		Optional<ShiftId> si;
		if((si = findById(id.getDate(), id.getShift())).isPresent()) {
			return si.get().getId();
		} else {
			UUID uuid = UUID.randomUUID();
			jdbcTemplate.update("INSERT INTO shift_id"
					+ "(id,shift_order,shift_date) VALUES(?,?,?) "
					+ "ON CONFLICT (shift_order,shift_date) DO NOTHING",
					uuid,
					id.getShift().toString(),
					id.getDate());
			return uuid;
		}
	}
}