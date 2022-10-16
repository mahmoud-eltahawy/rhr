package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.enums.ShiftOrder;

public class ShiftIdRowMapper implements RowMapper<ShiftId> {
	@Override
	public ShiftId mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ShiftId((UUID) rs.getObject("id")
				,rs.getDate("shift_date"),
				ShiftOrder.valueOf(rs.getString("shift_order")));
	}
}
