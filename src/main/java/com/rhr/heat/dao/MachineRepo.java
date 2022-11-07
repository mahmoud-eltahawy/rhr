package com.rhr.heat.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.MachineRowMapper;
import com.rhr.heat.entity.Machine;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MachineRepo {
	private final JdbcTemplate jdbcTemplate;

	public List<Machine> findAll() {
		String sql = "SELECT m.id, m.category, m.num FROM machine m";
		return jdbcTemplate.query(sql, new MachineRowMapper());
	}
	
	public List<Machine> findByCatagory(String catagory) {
		String sql = "SELECT m.id, m.category, m.num FROM machine m WHERE m.catagory = ?";
		return jdbcTemplate.query(sql, new MachineRowMapper(), catagory);
	}
	
	public List<Integer> findCatagoryAllNums(String catagory) {
		String sql = "SELECT DISTINCT m.num FROM machine m WHERE m.category = ?";
		return jdbcTemplate.queryForList(sql, Integer.class, catagory);
	}
	
	public List<String> findAllCatagories() {
		String sql = "SELECT DISTINCT m.category FROM machine m";
		return jdbcTemplate.queryForList(sql, String.class);
	}
	
	public Optional<Machine> findById(UUID id) {
		String sql = "SELECT m.id, m.category, m.num FROM machine m where m.id = ?";
		return jdbcTemplate.query(sql,
				new MachineRowMapper(), id).stream().findFirst();
	}
	
	public Optional<Machine> findByTheId(String catagory,Integer num) {
		String sql = "SELECT m.id, m.category, m.num FROM machine m "
				+ "WHERE m.category = ? AND m.num = ?";
		return jdbcTemplate.query(sql,
				new MachineRowMapper(), catagory, num).stream().findFirst();
	}
	
	public int deleteById(UUID id) {
		String sql = "DELETE FROM machine where id =?";
		return jdbcTemplate.update(sql, id);
	}
	
	public int deleteBytheId(String catagory,Integer num) {
		String sql = "DELETE FROM machine WHERE category = ? and num = ?";
		return jdbcTemplate.update(sql, catagory, num);
	}

	public List<UUID> saveAll(List<Machine> emps) {
		return emps.stream()
				.map(e -> {return save(e);})
				.collect(Collectors.toList());
	}

	public UUID save(Machine machine) {
		Optional<Machine> m;
		if((m =findByTheId(machine.getCatagory(),machine.getNumber())).isPresent()) {
			return m.get().getId();
		} else {
			UUID uuid = UUID.randomUUID();
			jdbcTemplate.update("INSERT INTO machine"
					+ "(id,category, num) VALUES(?,?,?) "
					+ "ON CONFLICT(category,num) DO NOTHING",
					uuid,
					machine.getCatagory(),
					machine.getNumber());
			return uuid;
		}
	}
}
