package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.enums.ShiftType;
import com.rhr.heat.model.ShiftId;
import com.rhr.heat.model.plate.MyDate;

public class ShiftIdRowMapper implements RowMapper<ShiftId> {
	@Override
	public ShiftId mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ShiftId(new MyDate(rs.getInt("my_year")
						,rs.getInt("my_month"),
						rs.getInt("my_day"))
				, ShiftType.valueOf(rs.getString("shift_order")));
	}

}
