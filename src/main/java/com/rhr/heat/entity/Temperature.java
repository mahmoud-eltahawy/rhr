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
public class Temperature {
	private UUID id;
	private ShiftId shiftId;
	private Machine machine;
	private Integer max;
	private Integer min;
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(shiftId != null) {
			canPush.addAll(shiftId.isPushable());
		} else {
			canPush.add(Pushable.TEMPERATURE_SHIFT_ID_IS_NULL);
		}
		if(machine != null) {
			canPush.addAll(machine.isPushable());
		} else {
			canPush.add(Pushable.TEMPERATURE_MACHINE_IS_NULL);
		}
		if(max == null) {
			canPush.add(Pushable.TEMPERATURE_MAXIMUM_IS_NULL);
		}
		if(min == null) {
			canPush.add(Pushable.TEMPERATURE_MINIMUM_IS_NULL);
		}
		if(max < min) {
			canPush.add(Pushable.TEMPERATURE_MAX_LESS_THAN_MIN);
		}
		return canPush;
	}
}
