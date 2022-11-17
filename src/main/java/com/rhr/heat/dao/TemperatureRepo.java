package com.rhr.heat.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.TemperatureRowMapper;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.enums.Pushable;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TemperatureRepo {
	private final JdbcTemplate jdbcTemplate;
	private final MachineRepo machineRepo;

	public List<Temperature> findAll() {
		return jdbcTemplate.query(
				"SELECT t.* FROM temperature t",
				new TemperatureRowMapper())
				.stream().map(t -> fullFill(t))
				.collect(Collectors.toList());
	}
	
	public Optional<Temperature> findById(UUID id) {
		Optional<Temperature> temp = jdbcTemplate.query
				("SELECT t.* FROM temperature t WHERE t.id =?",
				new TemperatureRowMapper(), id).stream().findFirst();
		if(temp.isPresent()) {
			return Optional.of(fullFill(temp.get()));
		} else {
			return temp;
		}
	}
	
	public List<Temperature> findByShiftId(UUID id){
		return jdbcTemplate.query(
				"SELECT t.* FROM temperature t WHERE t.shift_id = ?"
				, new TemperatureRowMapper(),id)
				.stream().map(t -> fullFill(t))
				.collect(Collectors.toList());
	}
	
	public List<Temperature> findByMachineId(UUID id) {
		return jdbcTemplate.query("""
				SELECT t.* FROM temperature t
				WHERE t.machine_id =?
				""", new TemperatureRowMapper(), id)
				.stream().map(t -> fullFill(t))
				.collect(Collectors.toList());
	}
	
	public List<Temperature> findByMachineIdOnShift(UUID machineId,UUID ShiftId) {
		return jdbcTemplate.query("""
				SELECT t.* FROM temperature t
				WHERE t.machine_id =? and shift_id=?
				""", new TemperatureRowMapper(), machineId,ShiftId)
				.stream().map(t -> fullFill(t))
				.collect(Collectors.toList());
	}
	
	public int deleteById(UUID id) {
		return jdbcTemplate.update("DELETE FROM temperature t WHERE t.id =?", id);
	}
	
	public List<Pushable> saveAll(List<Temperature> tmps) {
		List<Pushable> result = new ArrayList<>();
		for (Temperature temperature : tmps) {
			result.addAll(save(temperature));
		}
		return result;
	}

	public List<Pushable> save(Temperature temperature) {
		List<Pushable> result = temperature.isPushable();
		if(result.isEmpty()) {
			jdbcTemplate.update("""
					INSERT INTO temperature
					(id,shift_id,machine_id,max_temp,min_temp)
					VALUES(?,?,?,?,?) ON CONFLICT(id) DO NOTHING
					""",
					temperature.getId(),
					temperature.getMachine().getId(),
					temperature.getMax(),
					temperature.getMin());
		}
		return result;
	}
	
	private Temperature fullFill(Temperature temp) {
//		Optional<ShiftId> shiftId = shiftIdRepo.findById(temp.getShiftId().getId());
		Optional<Machine> machine = machineRepo.findById(temp.getMachine().getId());
		if( machine.isPresent()) {
//			temp.setShiftId(shiftId.get());
			temp.setMachine(machine.get());
			return temp;
		}
		return null;
	}
}
