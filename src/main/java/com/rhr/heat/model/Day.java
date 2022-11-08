package com.rhr.heat.model;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import com.rhr.heat.entity.topLayer.ShiftFamily;
import com.rhr.heat.enums.ShiftOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Day {
	private ShiftFamily one;
	private ShiftFamily two;
	private ShiftFamily three;
	
	public static <T extends ShiftFamily> TreeMap<Date, Day> getDays(List<T> shifts){
		TreeMap<Date, Day> days = new TreeMap<Date,Day>(Collections.reverseOrder());
		for (T shift : shifts) {
			if(days.get(shift.getShiftId().getDate()) == null) {
				Day day = new Day();
				if(shift.getShiftId().getShift() == ShiftOrder.FIRST) {
					day.setOne(shift);
				} else if(shift.getShiftId().getShift() == ShiftOrder.SECOND) {
					day.setTwo(shift);
				} else if(shift.getShiftId().getShift() == ShiftOrder.THIRD) {
					day.setThree(shift);
				} 
				days.put(shift.getShiftId().getDate(), day);
			} else {
				Day day = days.get(shift.getShiftId().getDate());
				if(shift.getShiftId().getShift() == ShiftOrder.FIRST) {
					day.setOne(shift);
				} else if(shift.getShiftId().getShift() == ShiftOrder.SECOND) {
					day.setTwo(shift);
				} else if(shift.getShiftId().getShift() == ShiftOrder.THIRD) {
					day.setThree(shift);
				} 
				days.put(shift.getShiftId().getDate(), day);
			}
		}
		return days;
	}
}
