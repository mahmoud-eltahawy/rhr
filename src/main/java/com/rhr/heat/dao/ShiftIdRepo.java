package com.rhr.heat.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.ShiftIdRowMapper;
import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.model.ShiftId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShiftIdRepo {
	private final JdbcTemplate jdbcTemplate;
	
	public List<ShiftId> findAll() {
		String sql = "SELECT * FROM shift_id";
		return jdbcTemplate.query(sql, new ShiftIdRowMapper());
	}
	
	public Optional<ShiftId> findById(Long id) {
		String sql = "SELECT * FROM shift_id WHERE id = ?";
		return jdbcTemplate.query(sql,
				new ShiftIdRowMapper(), id).stream().findFirst();
	}
	
	public Optional<ShiftId> findById(Date date,ShiftOrder order) {
		String sql = "SELECT si.* FROM shift_id si WHERE si.shift_date = ? and si.shift_order = ?";
		return jdbcTemplate.query(sql,
				new ShiftIdRowMapper(), date,order.toString()).stream().findFirst();
	}
	
	public int deleteById(Long id) {
		String sql = "DELETE FROM shift_id WHERE id =?";
		return jdbcTemplate.update(sql, id);
	}

	public List<Long> saveAll(List<ShiftId> ids) {
		return ids.stream()
				.map(i -> {return save(i);})
				.collect(Collectors.toList());
	}

	public Long save(ShiftId id) {
		Optional<ShiftId> si;
		if((si = findById(id.getDate(), id.getShift())).isPresent()) {
			return si.get().getId();
		} else {
			KeyHolder key = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement("INSERT INTO shift_id"
					+ "(shift_order,shift_date) VALUES(?,?) "
					+ "ON CONFLICT (shift_order,shift_date) DO NOTHING", 
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, id.getShift().toString());
				ps.setDate(2, id.getDate());
				return ps;
			},key);
			
			if (key.getKeys().size() > 1) {
				return (Long)key.getKeys().get("id");
			} else {
				return key.getKey().longValue();
			}
		}
	}
}