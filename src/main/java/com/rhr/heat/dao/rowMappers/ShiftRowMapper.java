package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.model.Shift;
import com.rhr.heat.model.ShiftId;

public class ShiftRowMapper implements RowMapper<Shift> {
	@Override
	public Shift mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Shift(new ShiftId(rs.getLong("shift_id")
				, rs.getDate("shift_date"),
				ShiftOrder.valueOf(rs.getString("shift_order"))),
				null, null, null,
				rs.getString("notes"),
				rs.getInt("min_temp"),
				rs.getInt("max_temp"));
	}

}
