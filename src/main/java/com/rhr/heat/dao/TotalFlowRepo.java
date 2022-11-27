package com.rhr.heat.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.TotalFlowRowMapper;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.enums.Pushable;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TotalFlowRepo {
	private final JdbcTemplate jdbcTemplate;
	private final MachineRepo machineRepo;
	
	public Optional<TotalFlow> findById(UUID id) {
		Optional<TotalFlow> tf = jdbcTemplate.query(
				"SELECT * FROM total_flow WHERE id = ?", 
				new TotalFlowRowMapper(),id)
				.stream().findFirst();
		if(tf.isPresent()) {
			return Optional.of(fullFill(tf.get()));
		} else {
			return tf;
		}
	}
	
	public List<TotalFlow> findByShiftId(UUID id){
		return jdbcTemplate.query("""
				SELECT tf.* FROM total_flow tf
				WHERE tf.shift_id =?
				""", new TotalFlowRowMapper(),id)
				.stream().map(tf -> fullFill(tf))
				.collect(Collectors.toList());
	}
	
	public int deletByShiftId(UUID id){
		return jdbcTemplate.update("""
				DELETE FROM total_flow tf
				WHERE tf.shift_id =?
				""",id);
	}
	
	public int deleteFromShift(UUID shiftId){
		return jdbcTemplate.update("""
				DELETE FROM total_flow tf 
				WHERE tf.end_time = (SELECT MAX(tf2.end_time)
				from total_flow tf2) AND tf.shift_id = ?
				""",shiftId);
	}
	
	public List<TotalFlow> findAll(){
		return jdbcTemplate.query("SELECT * FROM total_flow", new TotalFlowRowMapper())
				.stream().map(tf -> fullFill(tf)).collect(Collectors.toList());
	}

	public List<Pushable> saveAll(List<TotalFlow> totalFlowAverage) {
		List<Pushable> result = new ArrayList<>();
		for (TotalFlow totalFlow : totalFlowAverage) {
			result.addAll(save(totalFlow));
		}
		return result;
	}

	public List<Pushable> save(TotalFlow tf) {
		List<Pushable> result = tf.isPushable();
		if(result.isEmpty()) {
			jdbcTemplate.update("""
					INSERT INTO total_flow(id,shift_id,
					begin_time, end_time, min_flow, max_flow) 
					VALUES(?,?,?,?,?,?) ON CONFLICT(id) DO NOTHING
					""",
					tf.getId(),
					tf.getShiftId(),
					tf.getCaseBeginTime(),
					tf.getCaseEndTime(),
					tf.getMinFlow(),
					tf.getMaxFlow());
			
			tf.getSuspendedMachines().forEach(m ->machineRepo
					.saveToTotalFlow(tf.getId(), m.getId()));
		}
		return result;
	}

	public TotalFlow fullFill(TotalFlow tf) {
		tf.setSuspendedMachines(machineRepo.findFromTotalFlow(tf.getId()));
		return tf;
	}
}
