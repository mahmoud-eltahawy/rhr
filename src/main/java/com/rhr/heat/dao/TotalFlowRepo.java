package com.rhr.heat.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.TotalFlowRowMapper;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.enums.Machine;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TotalFlowRepo {
	private final JdbcTemplate jdbcTemplate;
	
	public List<TotalFlow> findAll(){
		String sql = "SELECT * FROM total_flow";
		return jdbcTemplate.query(sql, new TotalFlowRowMapper())
				.stream().map(tf -> fullFill(tf)).collect(Collectors.toList());
	}
	
	public Optional<TotalFlow> findById(UUID id) {
		String sql = "SELECT * FROM total_flow WHERE id = ?";
		Optional<TotalFlow> tf = jdbcTemplate.query(sql, 
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
		jdbcTemplate.update("INSERT INTO total_flow(id,"
							+ "begin_time, end_time, min_flow, max_flow) "
							+ "VALUES(?,?,?,?,?)",
							uuid,
							tf.getCaseBeginTime(),
							tf.getCaseEndTime(),
							tf.getMinFlow(),
							tf.getMaxFlow());
		
		tf.getSuspendedMachines().forEach(m ->{
			jdbcTemplate.update("INSERT INTO "
					+ "suspended_machine(id,machine) values(?,?) "
					+ "ON CONFLICT(id,machine) DO NOTHING",uuid,m.toString());
		});
		
		return uuid;
	}
	
	private TotalFlow fullFill(TotalFlow tf) {
		tf.setSuspendedMachines(jdbcTemplate.queryForList("SELECT machine FROM "
				+ "suspended_machine where id =?",String.class,tf.getId())
				.stream().map(s -> Machine.valueOf(s)).collect(Collectors.toList()));
		return tf;
	}
}
