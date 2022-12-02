package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.entity.Category;
import com.rhr.heat.entity.Machine;

public class MachineRowMapper implements RowMapper<Machine> {

	@Override
	public Machine mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Machine(
				(UUID) rs.getObject("id")
				,new Category(rs.getString("cat_name"),null,null) ,
				rs.getInt("num"));
	}

}
