package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Temperature;

public class TemperatureRowMapper implements RowMapper<Temperature> {

	@Override
	public Temperature mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Temperature((UUID) rs.getObject("id"),
				(UUID) rs.getObject("shift_id"),
				new Machine((UUID) rs.getObject("machine_id"),null, null),
				rs.getInt("max_temp"),
				rs.getInt("min_temp"));
	}
}
