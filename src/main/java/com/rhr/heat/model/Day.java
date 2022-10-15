package com.rhr.heat.model;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rhr.heat.entity.Shift;
import com.rhr.heat.enums.ShiftOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Day {
	private Shift one;
	private Shift two;
	private Shift three;
	
	public static Map<Date, Day> getDays(List<Shift> shifts){
		Map<Date, Day> days = new HashMap<>();
		for (Shift shift : shifts) {
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
