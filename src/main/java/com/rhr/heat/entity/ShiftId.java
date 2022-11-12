package com.rhr.heat.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.rhr.heat.Tools;
import com.rhr.heat.enums.Pushable;
import com.rhr.heat.enums.ShiftOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftId {
	private UUID id;
	private Date date;
	private ShiftOrder shift;
	
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiftId other = (ShiftId) obj;
		return Tools.equals(date, other.date) && shift == other.shift;
	}
	@Override
	public int hashCode() {
		return Objects.hash(date, shift);
	}
}
