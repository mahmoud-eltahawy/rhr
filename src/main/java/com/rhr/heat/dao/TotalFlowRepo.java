package com.rhr.heat.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
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
	
	public void saveAll(List<TotalFlow> totalFlowAverage) {
		totalFlowAverage.forEach(t -> save(t));
	}

	public Long save(TotalFlow tf) {
		String sql = "INSERT INTO total_flow"
				+ "(consumers_case,"
				+ "begin_hour,begin_minute,"
				+ "end_hour,end_minute,"
				+ "min_flow,max_flow) "
				+ "VALUES(?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,
				tf.getConsumersCase().toString(),
				tf.getCaseBeginTime().getHour(),
				tf.getCaseBeginTime().getMinute(),
				tf.getCaseEndTime().getHour(),
				tf.getCaseEndTime().getMinute(),
				tf.getMinFlow(),
				tf.getMaxFlow());
		return (long) tf.hashCode();
	}
}