package com.rhr.heat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.rhr.heat.enums.Pushable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Temperature extends Identity {
	private UUID shiftId;
	private Machine machine;
	private Integer max;
	private Integer min;

	public Temperature(UUID id, UUID shiftId, Machine machine, Integer max, Integer min) {
		super(id);
		this.shiftId = shiftId;
		this.machine = machine;
		this.max = max;
		this.min = min;
	}

	public Temperature(UUID id) {
		super(id);
	}
	
	@Override
	public Boolean isSameAs(Identity identity) {
		Temperature other = (Temperature) identity;
		if(machine.equals(other.machine)) {
			return true;
		}
		return false;
	}
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(id == null) {
			canPush.add(Pushable.TEMPERATURE_ID_IS_NULL);
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
