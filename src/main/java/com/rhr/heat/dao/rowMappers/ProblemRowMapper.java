package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.entity.Problem;

public class ProblemRowMapper implements RowMapper<Problem> {
	@Override
	public Problem mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Problem(rs.getString("title"),
				rs.getString("description"));
	}
}
