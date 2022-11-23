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
		return jdbcTemplate.query(
				"SELECT * FROM shift",
				new ShiftIdRowMapper());
	}
	
	public List<ShiftId> findAll(Date date) {
		return jdbcTemplate.query(
				 "select * from shift s where s.shift_date = ?",
				new ShiftIdRowMapper(), date);
	}
	
	public List<ShiftId> findAll(ShiftOrder order) {
		return jdbcTemplate.query(
				 "select * from shift s where s.shift_order = ?",
				new ShiftIdRowMapper(), order.toString());
	}
	
	public Optional<ShiftId> findById(UUID id) {
		return jdbcTemplate.query("SELECT * FROM shift WHERE id = ?",
				new ShiftIdRowMapper(), id).stream().findFirst();
	}
	
	public Optional<ShiftId> findById(Date date,ShiftOrder order) {
		return jdbcTemplate.query(
				"SELECT s.* FROM shift s WHERE s.shift_date = ? and s.shift_order = ?",
				new ShiftIdRowMapper(), date,order.toString()).stream().findFirst();
	}

	public List<ShiftId> findOlderThan(Date date) {
		 return jdbcTemplate.query(
				 "select s.* from shift s "
				+ "where s.shift_date <= ?",
				new ShiftIdRowMapper(),date);
	}

	public List<ShiftId> findOlderThan(Date date,ShiftOrder order) {
		 return jdbcTemplate.query(
				 "select * from shift s "
				+ "where s.shift_date <= ? "
				+ "and s.shift_order = ?",
				new ShiftIdRowMapper(),date ,order.toString());
	}

	public List<ShiftId> findRecent(Date date) {
		 return jdbcTemplate.query(
				 "select * from shift s  "
				+ "where s.shift_date >= ?",
				new ShiftIdRowMapper(),date);
	}

	public List<ShiftId> findRecent(Date date, ShiftOrder order) {
		 return jdbcTemplate.query(
				 "select * from shift s "
				+ "where s.shift_date >= ? "
				+ "and s.shift_order = ?",
				new ShiftIdRowMapper(),date ,order.toString());
	}

	public List<ShiftId> findLast(Integer num) {
		 return jdbcTemplate.query(
				 "select * from shift s "
				+ " order by s.shift_date desc limit ?",
				new ShiftIdRowMapper(), num);
	}

	public List<ShiftId> findFromTo(Integer from,Integer to) {
		 return jdbcTemplate.query(
				 "select * from shift s "
				+ " order by s.shift_date desc offset ? limit ?",
				new ShiftIdRowMapper(), from, to);
	}

	public List<ShiftId> findBetween(Date older,Date newer) {
		 return jdbcTemplate.query(
				 "select * from shift s "
				+ "where s.shift_date between ? and ?",
				new ShiftIdRowMapper(),older,newer);
	}

	public List<ShiftId> findBetween(Date older, Date newer, ShiftOrder order) {
		 return jdbcTemplate.query(
				 "select * from shift s "
				+ "where s.shift_date between ? and ? and s.shift_order = ?",
				new ShiftIdRowMapper(),older,newer,order.toString());
	}
	
	public int deleteById(UUID id) {
		return jdbcTemplate.update("DELETE FROM shift WHERE id =?", id);
	}

	public List<Optional<UUID>> saveAll(List<ShiftId> ids) {
		return ids.stream().map(i -> save(i))
			.collect(Collectors.toList());
	}

	public Optional<UUID> save(ShiftId id) {
		UUID uuid = UUID.randomUUID();
			int cols = jdbcTemplate.update("INSERT INTO shift"
					+ "(id,shift_order,shift_date) VALUES(?,?,?) "
					+ "ON CONFLICT (shift_order,shift_date) DO NOTHING",
					uuid,
					id.getShift().toString(),
					id.getDate());
		if(cols == 0) {
			return Optional.ofNullable(null);
		}else {
			return Optional.of(uuid);
		}
		
	}
}