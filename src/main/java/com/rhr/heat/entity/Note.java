package com.rhr.heat.entity;

import java.util.UUID;

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
	
	public Boolean isPushable() {
		if(shiftId.getId() != null && note != null) {
			return true;
		}
		return false;
	}
}
