package com.rhr.heat.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temperature {
	private UUID id;
	private ShiftId shiftId;
	private Machine machine;
	private Integer max;
	private Integer min;
	
	public Boolean isPushable() {
		if(shiftId != null && machine != null && max != null && min != null) {
			if(shiftId.isPushable() && machine.isPushable() && max >= min) {
				return true;
			}
		}
		return false;
	}
}
