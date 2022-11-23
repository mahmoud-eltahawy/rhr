package com.rhr.heat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.rhr.heat.enums.Pushable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note extends Identity {
	private String note;
	
	@Override
	public Boolean isSameAs(Identity identity) {
		Note other = (Note) identity;
		if(note.equals(other.note)) {
			return true;
		}
		return false;
	}

	public Note(UUID id, String note) {
		super(id);
		this.note = note;
	}

	public Note(UUID id) {
		super(id);
	}
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		
		if(note == null) {
			canPush.add(Pushable.NOTE_IS_EMPTY);
		}
		return canPush;
	}
}
