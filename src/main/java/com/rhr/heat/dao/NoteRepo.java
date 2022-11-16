package com.rhr.heat.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.NoteRowMapper;
import com.rhr.heat.entity.Note;
import com.rhr.heat.enums.Pushable;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NoteRepo {
	private final JdbcTemplate jdbcTemplate;
	private final ShiftIdRepo shiftIdRepo;

	public List<Note> findAll() {
		return jdbcTemplate.query(
				"SELECT n.* FROM notes n",
				new NoteRowMapper())
				.stream().map(t -> fullFill(t))
				.collect(Collectors.toList());
	}
	
	public Optional<Note> findById(UUID id) {
		Optional<Note> temp = jdbcTemplate.query
				("SELECT n.* FROM notes n WHERE n.id =?",
				new NoteRowMapper(), id).stream().findFirst();
		if(temp.isPresent()) {
			return Optional.of(fullFill(temp.get()));
		} else {
			return temp;
		}
	}
	
	public List<Note> findByShiftId(UUID id){
		return jdbcTemplate.query(
				"SELECT n.* FROM notes n WHERE n.shift_id = ?"
				, new NoteRowMapper(),id)
				.stream().map(t -> fullFill(t))
				.collect(Collectors.toList());
	}
	
	public int deleteById(UUID id) {
		return jdbcTemplate.update("DELETE FROM notes n WHERE n.id =?", id);
	}
	
	public List<Pushable> saveAll(List<Note> notes) {
		List<Pushable> result = new ArrayList<>();
		for (Note note : notes) {
			result.addAll(save(note));
		}
		return result;
	}

	public List<Pushable> save(Note note) {
		List<Pushable> result = note.isPushable();
		if(result.isEmpty()) {
			jdbcTemplate.update("""
					INSERT INTO notes(id,shift_id,note)
					VALUES(?,?,?) ON CONFLICT(id) DO NOTHING
					""",
					note.getId(),
					note.getShiftId().getId(),
					note.getNote());
		}
		return result;
	}
	
	private Note fullFill(Note note) {
		note.setShiftId(shiftIdRepo.findById(note.getShiftId().getId()).get());
		return note;
	}
}
