package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.ProblemDetail;


public class ProblemDetailRowMapper implements RowMapper<ProblemDetail>{
	@Override
	public ProblemDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ProblemDetail((UUID) rs.getObject("id"),
				null,
				new Machine((UUID) rs.getObject("machine_id"), null, null),
				rs.getTime("begin_time"),
				rs.getTime("end_time"));
	}
}