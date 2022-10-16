package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.entity.Shift;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.enums.ShiftOrder;

public class ShiftRowMapper implements RowMapper<Shift> {
	@Override
	public Shift mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Shift(new ShiftId((UUID) rs.getObject("shift_id")
				, rs.getDate("shift_date"),
				ShiftOrder.valueOf(rs.getString("shift_order"))),
				null, null, null,
				rs.getString("notes"),
				rs.getInt("min_temp"),
				rs.getInt("max_temp"));
	}

}
