package com.rhr.heat.dao;

import java.util.List;
import java.util.Optional;

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
	
	public Optional<TotalFlow> findById(Long id){
		String sql = "SELECT * FROM total_flow WHERE id = ?";
		return jdbcTemplate.query(sql,
				new TotalFlowRowMapper(),id)
				.stream().findFirst();
	}
	
	public void saveAll(List<TotalFlow> totalFlowAverage) {
		totalFlowAverage.forEach(t -> save(t));
	}

	public void save(TotalFlow tf2) {
		String sql = "INSERT INTO total_flow"
				+ "(consumers_case,"
				+ "begin_hour,begin_minute,"
				+ "end_hour,end_minute,"
				+ "min_flow,max_flow) "
				+ "VALUES(?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,
				tf2.getConsumersCase().toString(),
				tf2.getCaseBeginTime().getHour(),
				tf2.getCaseBeginTime().getMinute(),
				tf2.getCaseEndTime().getHour(),
				tf2.getCaseEndTime().getMinute(),
				tf2.getMinFlow(),
				tf2.getMaxFlow());
	}
}