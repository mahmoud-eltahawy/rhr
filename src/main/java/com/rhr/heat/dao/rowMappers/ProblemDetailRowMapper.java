package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.enums.Machine;

public class ProblemDetailRowMapper implements RowMapper<ProblemDetail>{
	@Override
	public ProblemDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ProblemDetail((UUID) rs.getObject("id"),
				null,
				Machine.valueOf(rs.getString("machine")),
				rs.getTime("begin_time"),
				rs.getTime("end_time"));
	}
}
