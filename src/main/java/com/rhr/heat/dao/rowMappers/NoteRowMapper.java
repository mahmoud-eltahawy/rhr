package com.rhr.heat.dao.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.ShiftId;

public class NoteRowMapper implements RowMapper<Note> {

	@Override
	public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Note((UUID) rs.getObject("id"),
				new ShiftId((UUID) rs.getObject("shift_id"), null, null),
				rs.getString("note"));
	}
}







