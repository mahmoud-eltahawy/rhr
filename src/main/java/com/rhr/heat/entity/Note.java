package com.rhr.heat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.rhr.heat.enums.Pushable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
	private UUID id;
	private ShiftId shiftId;
	private String note;
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		
		if(shiftId != null) {
			canPush = shiftId.isPushable();
		} else {
			canPush.add(Pushable.NOTE_SHIFT_ID_IS_NULL);
		}
		
		if(note == null) {
			canPush.add(Pushable.NOTE_IS_EMPTY);
		}
		return canPush;
	}
}
