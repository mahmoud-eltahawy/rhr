package com.rhr.heat.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.MachineRowMapper;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.enums.Pushable;

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
	
	public void removeFromTotalFlow(UUID tfId,UUID mId) {
		jdbcTemplate.update("""
					DELETE FROM total_flow_machine
					WHERE total_flow_id =? AND machine_id =?
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

	public List<Machine> allMachinesInFlow(UUID flowId){
		return jdbcTemplate.query("""
			SELECT m.* FROM machine m WHERE m.id 
			IN (SELECT machine_id FROM 
			total_flow_machine WHERE total_flow_id =?)
		""",new MachineRowMapper(), flowId);
	}
	
	public List<Integer> findCatagoryAllNums(String category) {
		return jdbcTemplate.queryForList(
				"SELECT DISTINCT m.num FROM machine m WHERE m.category = ?",
				Integer.class, category);
	}
	
	public List<String> findAllCatagories() {
		return jdbcTemplate.queryForList(
				"SELECT DISTINCT m.category FROM machine m",
				String.class);
	}
	
	public List<String> findNoneHeadersCatagories() {
		return jdbcTemplate.queryForList("""
					SELECT DISTINCT m.category
					FROM machine m WHERE m.num <> 0
				""",String.class);
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

	public List<Pushable> saveAll(List<Machine> machines) {
		List<Pushable> result = new ArrayList<>();
		for (Machine machine : machines) {
			result.addAll(save(machine));
		}
		return result;
	}

	public List<Pushable> save(Machine machine) {
		List<Pushable> result = machine.isPushable();
		if(result.isEmpty()) {
			jdbcTemplate.update("""
					INSERT INTO machine
					(id,category, num) VALUES(?,?,?)
					ON CONFLICT(category,num) DO NOTHING
					""",
					machine.getId(),
					machine.getCategory(),
					machine.getNumber());
		}
		return result;
	}
}
