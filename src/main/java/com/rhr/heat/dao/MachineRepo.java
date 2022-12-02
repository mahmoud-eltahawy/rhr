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
	private final CategoryRepo categoryRepo;
	
	public int saveToTotalFlow(UUID tfId,UUID mId) {
		return jdbcTemplate.update("""
					INSERT INTO total_flow_machine(total_flow_id,machine_id)
					values(?,?) ON CONFLICT(total_flow_id,machine_id) DO NOTHING
				""",tfId,mId);
	}
	
	public int removeFromTotalFlow(UUID tfId,UUID mId) {
		return jdbcTemplate.update("""
					DELETE FROM total_flow_machine
					WHERE total_flow_id =? AND machine_id =?
				""",tfId,mId);
	}
	
	public List<Machine> findFromTotalFlow(UUID tfId) {
		return jdbcTemplate.queryForList("""
				SELECT machine_id FROM
				total_flow_machine where total_flow_id = ?
				""",UUID.class,tfId)
				.stream().map(uuid -> fullfill(findById(uuid).orElseThrow()))
				.collect(Collectors.toList());
	}

	public List<Machine> findAll() {
		return jdbcTemplate.query(
				"SELECT m.* FROM machine m",
				new MachineRowMapper()).stream()
				.map(m -> fullfill(m)).toList();
	}
	
	public List<Machine> findByCatagory(String catagory) {
		return jdbcTemplate.query(
				"SELECT m.* FROM machine m WHERE m.cat_name = ?",
				new MachineRowMapper(), catagory)
				.stream().map(m -> fullfill(m)).toList();
	}

	public List<Machine> allMachinesInFlow(UUID flowId){
		return jdbcTemplate.query("""
			SELECT m.* FROM machine m WHERE m.id 
			IN (SELECT machine_id FROM 
			total_flow_machine WHERE total_flow_id =?)
			""",new MachineRowMapper(), flowId)
			.stream().map(m -> fullfill(m)).toList();

	}
	
	public List<Integer> findCatagoryAllNums(String category) {
		return jdbcTemplate.queryForList(
				"SELECT DISTINCT m.num FROM machine m WHERE m.cat_name = ?",
				Integer.class, category);
	}
	
	
	public Optional<Machine> findById(UUID id) {
		Optional<Machine> machine = jdbcTemplate.query(
				"SELECT m.* FROM machine m where m.id = ?",
				new MachineRowMapper(), id).stream().findFirst();
		if(machine.isPresent()){
			return Optional.of(fullfill(machine.get()));
		} else {
			return machine;
		}
	}
	
	public Optional<Machine> findByTheId(String catagory,Integer num) {
		Optional<Machine> machine = jdbcTemplate.query("""
				SELECT m.* FROM machine m
				WHERE m.cat_name = ? AND m.num = ?
				""",
				new MachineRowMapper(), catagory, num).stream().findFirst();
		if(machine.isPresent()){
			return Optional.of(fullfill(machine.get()));
		} else {
			return machine;
		}
	}
	
	public int deleteById(UUID id) {
		return jdbcTemplate.update("DELETE FROM machine where id =?", id);
	}
	
	public int deleteBytheId(String catagory,Integer num) {
		return jdbcTemplate.update(
				"DELETE FROM machine WHERE cat_name = ? and num =?", catagory, num);
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
					(id,cat_name, num) VALUES(?,?,?)
					ON CONFLICT(cat_name,num) DO NOTHING
					""",
					machine.getId(),
					machine.getCategory().getName(),
					machine.getNumber());
		}
		return result;
	}

	public Machine fullfill(Machine machine){
		machine.setCategory(categoryRepo
			.findByName(machine.getCategory().getName()).orElseThrow());
		return machine;
	}
}
