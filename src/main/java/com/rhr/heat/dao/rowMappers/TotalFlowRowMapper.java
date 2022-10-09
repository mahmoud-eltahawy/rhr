package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.enums.ConsumersCase;
import com.rhr.heat.model.TotalFlow;
import com.rhr.heat.model.plate.MyTime;

public class TotalFlowRowMapper implements RowMapper<TotalFlow> {

	@Override
	public TotalFlow mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new TotalFlow(rs.getLong("id"),
				ConsumersCase.valueOf(rs.getString("consumers_case")),
				rs.getInt("min_flow"),
				rs.getInt("max_flow"),
				new MyTime(rs.getInt("begin_hour"),rs.getInt("end_minute")),
				new MyTime(rs.getInt("end_hour"),rs.getInt("end_minute")));
	}
}
