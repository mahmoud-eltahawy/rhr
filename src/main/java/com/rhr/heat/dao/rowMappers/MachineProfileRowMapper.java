package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.model.MachineProfile;

public class MachineProfileRowMapper implements RowMapper<MachineProfile>{
	@Override
	public MachineProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new MachineProfile(
				(UUID) rs.getObject("id"),
				rs.getDate("shift_date"),
				ShiftOrder.valueOf(rs.getString("shift_order")),
				rs.getTime("begin_time"),
				rs.getTime("end_time"),
				null);
	}
}
