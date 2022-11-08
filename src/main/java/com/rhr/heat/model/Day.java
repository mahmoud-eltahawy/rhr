package com.rhr.heat.model;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.enums.ShiftOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Day {
	private ShiftId one;
	private ShiftId two;
	private ShiftId three;
	
	public static TreeMap<Date, Day> getDays(List<ShiftId> shifts){
		TreeMap<Date, Day> days = new TreeMap<Date,Day>(Collections.reverseOrder());
		for (ShiftId shift : shifts) {
			if(days.get(shift.getDate()) == null) {
				Day day = new Day();
				if(shift.getShift() == ShiftOrder.FIRST) {
					day.setOne(shift);
				} else if(shift.getShift() == ShiftOrder.SECOND) {
					day.setTwo(shift);
				} else if(shift.getShift() == ShiftOrder.THIRD) {
					day.setThree(shift);
				} 
				days.put(shift.getDate(), day);
			} else {
				Day day = days.get(shift.getDate());
				if(shift.getShift() == ShiftOrder.FIRST) {
					day.setOne(shift);
				} else if(shift.getShift() == ShiftOrder.SECOND) {
					day.setTwo(shift);
				} else if(shift.getShift() == ShiftOrder.THIRD) {
					day.setThree(shift);
				} 
				days.put(shift.getDate(), day);
			}
		}
		return days;
	}
}
