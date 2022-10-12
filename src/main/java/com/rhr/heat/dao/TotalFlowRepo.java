package com.rhr.heat.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.TotalFlowRowMapper;
import com.rhr.heat.enums.Machine;
import com.rhr.heat.model.TotalFlow;

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
	
	public Optional<TotalFlow> findById(Long id) {
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

	public List<Long> saveAll(List<TotalFlow> totalFlowAverage) {
		return totalFlowAverage.stream()
				.map(t -> {return save(t);})
				.collect(Collectors.toList());
	}

	public Long save(TotalFlow tf) {
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(connection ->{
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO total_flow("
							+ "begin_time, end_time, min_flow, max_flow) "
							+ "VALUES(?,?,?,?)", 
							Statement.RETURN_GENERATED_KEYS);
			ps.setTime(1, tf.getCaseBeginTime());
			ps.setTime(2, tf.getCaseEndTime());
			ps.setInt(3, tf.getMinFlow());
			ps.setInt(4, tf.getMaxFlow());
			return ps;
		},key);
		
		final Long id;
		if (key.getKeys().size() > 1) {
			id = (Long)key.getKeys().get("id");
		} else {
			id = key.getKey().longValue();
		}
		tf.getSuspendedMachines().forEach(m ->{
			jdbcTemplate.update("INSERT INTO "
					+ "suspended_machine(id,machine) values(?,?) "
					+ "ON CONFLICT(id,machine) DO NOTHING",id,m.toString());
		});
		
	    return id;
	}
	
	private TotalFlow fullFill(TotalFlow tf) {
		tf.setSuspendedMachines(jdbcTemplate.queryForList("SELECT machine FROM "
				+ "suspended_machine where id =?",String.class,tf.getId())
				.stream().map(s -> Machine.valueOf(s)).collect(Collectors.toList()));
		return tf;
	}
}
