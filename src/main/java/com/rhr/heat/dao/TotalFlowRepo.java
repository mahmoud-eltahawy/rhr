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
import com.rhr.heat.model.TotalFlow;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TotalFlowRepo {
	private final JdbcTemplate jdbcTemplate;
	
	public List<TotalFlow> findAll(){
		String sql = "SELECT * FROM total_flow";
		return jdbcTemplate.query(sql, new TotalFlowRowMapper());
	}
	
	public Optional<TotalFlow> findById(Long id) {
		String sql = "SELECT * FROM total_flow WHERE id = ?";
		return jdbcTemplate.query(sql, 
				new TotalFlowRowMapper(),id)
				.stream().findFirst();
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
					.prepareStatement("INSERT INTO total_flow(consumers_case,"
							+ "begin_time, end_time, min_flow, max_flow) "
							+ "VALUES(?,?,?,?,?)", 
							Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, tf.getAtmsCase().toString());
			ps.setTime(2, tf.getCaseBeginTime());
			ps.setTime(3, tf.getCaseEndTime());
			ps.setInt(4, tf.getMinFlow());
			ps.setInt(5, tf.getMaxFlow());
			return ps;
		},key);
	 if (key.getKeys().size() > 1) {
			return (Long)key.getKeys().get("id");
		} else {
			return key.getKey().longValue();
		}
	}
}
