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
	private ShiftId shiftId;
	private String note;

	public Note(UUID id, ShiftId shiftId, String note) {
		super(id);
		this.shiftId = shiftId;
		this.note = note;
	}

	public Note(UUID id) {
		super(id);
	}
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		
		if(shiftId != null) {
			canPush = shiftId.isPushable();
		} else {
			canPush.add(Pushable.NOTE_SHIFT_ID_IS_NULL);
		}
		
		if(id == null) {
			canPush.add(Pushable.NOTE_ID_NULL);
		}
		if(note == null) {
			canPush.add(Pushable.NOTE_IS_EMPTY);
		}
		return canPush;
	}
}
