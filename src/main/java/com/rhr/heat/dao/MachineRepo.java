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
	
	public void saveToTotalFlow(UUID tfId,UUID mId) {
		jdbcTemplate.update("""
					INSERT INTO total_flow_machine(total_flow_id,machine_id)
					values(?,?) ON CONFLICT(total_flow_id,machine_id) DO NOTHING
				""",tfId,mId);
	}
	
	public List<Machine> findFromTotalFlow(UUID tfId) {
		return jdbcTemplate.queryForList("""
				SELECT machine_id FROM
				total_flow_machine where total_flow_id = ?
				""",UUID.class,tfId)
				.stream().map(uuid -> findById(uuid).orElseThrow())
				.collect(Collectors.toList());
	}

	public List<Machine> findAll() {
		return jdbcTemplate.query(
				"SELECT m.* FROM machine m",
				new MachineRowMapper());
	}
	
	public List<Machine> findByCatagory(String catagory) {
		return jdbcTemplate.query(
				"SELECT m.* FROM machine m WHERE m.category = ?",
				new MachineRowMapper(), catagory);
	}
	
	public List<Integer> findCatagoryAllNums(String category) {
		return jdbcTemplate.queryForList(
				"SELECT DISTINCT m.num FROM machine m WHERE m.category = ?",
				Integer.class, category);
	}
	
	public List<String> findAllCatagories() {
		return jdbcTemplate.queryForList(
				"SELECT DISTINCT m.category FROM machine m", String.class);
	}
	
	public Optional<Machine> findById(UUID id) {
		return jdbcTemplate.query(
				"SELECT m.* FROM machine m where m.id = ?",
				new MachineRowMapper(), id).stream().findFirst();
	}
	
	public Optional<Machine> findByTheId(String catagory,Integer num) {
		return jdbcTemplate.query("""
				SELECT m.* FROM machine m
				WHERE m.category = ? AND m.num = ?
				""",
				new MachineRowMapper(), catagory, num).stream().findFirst();
	}
	
	public int deleteById(UUID id) {
		return jdbcTemplate.update("DELETE FROM machine where id =?", id);
	}
	
	public int deleteBytheId(String catagory,Integer num) {
		return jdbcTemplate.update(
				"DELETE FROM machine WHERE category = ? and num =?", catagory, num);
	}

	public List<UUID> saveAll(List<Machine> emps) {
		return emps.stream()
				.map(e -> save(e))
				.collect(Collectors.toList());
	}

	public UUID save(Machine machine) {
		Optional<Machine> m;
		if((m =findByTheId(machine.getCategory(),machine.getNumber())).isPresent()) {
			return m.get().getId();
		} else if(machine.isPushable()) {
			UUID uuid = null;
			if(machine.getId() != null) {
				uuid = machine.getId();
			} else {
				uuid = UUID.randomUUID();
			}
			jdbcTemplate.update("""
					INSERT INTO machine
					(id,category, num) VALUES(?,?,?)
					ON CONFLICT(category,num) DO NOTHING
					""",
					uuid,
					machine.getCategory(),
					machine.getNumber());
			return uuid;
		}
		return null;
	}
}
