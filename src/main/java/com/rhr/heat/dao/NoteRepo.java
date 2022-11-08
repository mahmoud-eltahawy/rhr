package com.rhr.heat.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.rowMappers.NoteRowMapper;
import com.rhr.heat.entity.Note;

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
	
	public List<UUID> saveAll(List<Note> tmps) {
		return tmps.stream()
				.map(t ->  save(t))
				.collect(Collectors.toList());
	}

	public UUID save(Note note) {
		UUID theId = UUID.randomUUID();
		jdbcTemplate.update("""
				INSERT INTO notes
				(id,shift_id,note)
				VALUES(?,?,?) ON CONFLICT(id) DO NOTHING
				""",theId,
				note.getShiftId().getId(),
				note.getNote());
		return theId;
	}
	
	private Note fullFill(Note note) {
		note.setShiftId(shiftIdRepo.findById(note.getShiftId().getId()).get());
		return note;
	}
}
