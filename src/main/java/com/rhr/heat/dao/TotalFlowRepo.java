package com.rhr.heat.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.TotalFlowRowMapper;
import com.rhr.heat.entity.TotalFlow;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TotalFlowRepo {
	private final JdbcTemplate jdbcTemplate;
	private final MachineRepo machineRepo;
	
	public void saveToShift(TotalFlow tf,UUID shiftId) {
				UUID tId = null;
				if(tf.getId() != null) {
					tId = tf.getId();
				} else {
					tId = save(tf);
				}
				jdbcTemplate.update("INSERT INTO shift_total_flow"
						+ "(shift_id,total_flow_id) VALUES(?,?)",shiftId,tId);
	}
	
	public List<TotalFlow> findByShiftId(UUID id){
		return jdbcTemplate.query("""
				SELECT tf.* FROM total_flow tf
				JOIN shift_total_flow stf 
				ON tf.id = stf.total_flow_id
				WHERE stf.shift_id =?
				""", new TotalFlowRowMapper(),id)
				.stream().map(tf -> fullFill(tf))
				.collect(Collectors.toList());
	}
	
	public List<TotalFlow> findAll(){
		return jdbcTemplate.query("SELECT * FROM total_flow", new TotalFlowRowMapper())
				.stream().map(tf -> fullFill(tf)).collect(Collectors.toList());
	}
	
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

	public List<UUID> saveAll(List<TotalFlow> totalFlowAverage) {
		return totalFlowAverage.stream()
				.map(t -> {return save(t);})
				.collect(Collectors.toList());
	}

	public UUID save(TotalFlow tf) {
		UUID uuid = UUID.randomUUID();
		jdbcTemplate.update("""
				INSERT INTO total_flow(id,
				begin_time, end_time, min_flow, max_flow) 
				VALUES(?,?,?,?,?)
				""",uuid,
				tf.getCaseBeginTime(),
				tf.getCaseEndTime(),
				tf.getMinFlow(),
				tf.getMaxFlow());
		
		tf.getSuspendedMachines().forEach(m ->{
			jdbcTemplate.update("""
					INSERT INTO total_flow_machine(total_flow_id,machine_id)
					values(?,?) ON CONFLICT(total_flow_id,machine_id) DO NOTHING
					""",uuid,m.getId());
		});
		return uuid;
	}

	public TotalFlow fullFill(TotalFlow tf) {
		tf.setSuspendedMachines(jdbcTemplate.queryForList("""
				SELECT machine_id FROM
				total_flow_machine where total_flow_id = ?
				""",UUID.class,tf.getId())
				.stream().map(uuid -> machineRepo.findById(uuid).orElseThrow())
				.collect(Collectors.toList()));
		return tf;
	}
}
