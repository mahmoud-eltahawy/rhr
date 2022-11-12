package com.rhr.heat.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.TemperatureRowMapper;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.Temperature;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TemperatureRepo {
	private final JdbcTemplate jdbcTemplate;
	private final MachineRepo machineRepo;
	private final ShiftIdRepo shiftIdRepo;

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
	
	public List<UUID> saveAll(List<Temperature> tmps) {
		return tmps.stream()
				.map(t ->  save(t))
				.collect(Collectors.toList());
	}

	public UUID save(Temperature temperature) {
		if(temperature.isPushable().isEmpty()) {
			UUID theId = null;
			if(temperature.getId() != null) {
				theId = temperature.getId();
			} else {
				theId = UUID.randomUUID();
			}
			jdbcTemplate.update("""
					INSERT INTO temperature
					(id,shift_id,machine_id,max_temp,min_temp)
					VALUES(?,?,?,?,?) ON CONFLICT(id) DO NOTHING
					""",theId,
					shiftIdRepo.save(temperature.getShiftId()),
					machineRepo.save(temperature.getMachine()),
					temperature.getMax(),
					temperature.getMin());
			return theId;
		}
		return null;
	}
	
	private Temperature fullFill(Temperature temp) {
		Optional<ShiftId> shiftId = shiftIdRepo.findById(temp.getShiftId().getId());
		Optional<Machine> machine = machineRepo.findById(temp.getMachine().getId());
		if(shiftId.isPresent() && machine.isPresent()) {
			temp.setShiftId(shiftId.get());
			temp.setMachine(machine.get());
			return temp;
		}
		return null;
	}
}
