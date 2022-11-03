package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.model.ProblemProfile;

public class ProblemProfileRowMapper implements RowMapper<ProblemProfile> {
	//TODO set machine
	@Override
	public ProblemProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ProblemProfile(rs.getDate("shift_date"),
				ShiftOrder.valueOf(rs.getString("shift_order")),
				rs.getTime("begin_time"),
				rs.getTime("end_time"),
				null);
	}
}

