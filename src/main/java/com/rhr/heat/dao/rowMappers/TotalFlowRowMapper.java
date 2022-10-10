package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.enums.AtmsCase;
import com.rhr.heat.model.TotalFlow;

public class TotalFlowRowMapper implements RowMapper<TotalFlow> {
	@Override
	public TotalFlow mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new TotalFlow(
				rs.getLong("id"),
				AtmsCase.valueOf(rs.getString("consumers_case")),
				rs.getInt("min_flow"),
				rs.getInt("max_flow"),
				rs.getTime("begin_Time"),
				rs.getTime("end_time"));
	}
}