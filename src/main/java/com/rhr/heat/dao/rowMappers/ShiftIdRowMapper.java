package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.model.ShiftId;

public class ShiftIdRowMapper implements RowMapper<ShiftId> {
	@Override
	public ShiftId mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ShiftId(rs.getLong("id")
				,rs.getDate("shift_date"),
				ShiftOrder.valueOf(rs.getString("shift_order")));
	}
}
