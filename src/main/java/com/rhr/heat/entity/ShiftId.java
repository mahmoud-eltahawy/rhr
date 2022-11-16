package com.rhr.heat.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.rhr.heat.enums.Pushable;
import com.rhr.heat.enums.ShiftOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShiftId extends Identity {
	private Date date;
	private ShiftOrder shift;

	public ShiftId(UUID id, Date date, ShiftOrder shift) {
		super(id);
		this.date = date;
		this.shift = shift;
	}

	public ShiftId(UUID id) {
		super(id);
	}
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(date == null) {
			canPush.add(Pushable.SHIFT_DATE_IS_NULL);
		}
		if(shift == null) {
			canPush.add(Pushable.SHIFT_ORDER_IS_NULL);
		}
		if(id == null) {
			canPush.add(Pushable.SHIFT_ID_IS_NULL);
		}
		return canPush;
	}
}
