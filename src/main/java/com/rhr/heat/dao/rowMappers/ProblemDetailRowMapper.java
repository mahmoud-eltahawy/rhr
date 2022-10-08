package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.enums.Machine;
import com.rhr.heat.enums.Problem;
import com.rhr.heat.model.ProblemDetail;
import com.rhr.heat.model.plate.MyTime;

public class ProblemDetailRowMapper implements RowMapper<ProblemDetail> {

	@Override
	public ProblemDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ProblemDetail(rs.getLong("id"),
				Problem.valueOf(rs.getString("problem")),
				Machine.valueOf(rs.getString("machine")),
				new MyTime(rs.getInt("begin_hour"),rs.getInt("Begin_minute")),
				new MyTime(rs.getInt("begin_hour"),rs.getInt("Begin_minute")));
	}

}
