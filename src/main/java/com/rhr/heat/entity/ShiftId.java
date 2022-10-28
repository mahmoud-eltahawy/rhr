package com.rhr.heat.entity;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

import com.rhr.heat.Tools;
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
