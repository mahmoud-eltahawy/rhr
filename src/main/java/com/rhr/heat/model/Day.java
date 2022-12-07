package com.rhr.heat.model;

import java.sql.Date;
import java.util.Calendar;
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
	
	public static TreeMap<String, Day> getDays(List<ShiftId> shifts){
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
		TreeMap<String, Day> result = new TreeMap<>(Collections.reverseOrder());
		days.forEach((k,v) -> {
			result.put(stringifyDate(k), v);
		});
		return result;
	}
	private static String stringifyDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Integer year =  cal.get(Calendar.YEAR);
		Integer month= cal.get(Calendar.MONTH);
		Integer day =  cal.get(Calendar.DAY_OF_MONTH);
		String smonth = month.toString();
		String sday = day.toString();
		if(smonth.length() == 1){
			smonth = "0"+ smonth;
		}
		if(sday.length() == 1){
			sday = "0"+ sday;
		}
		return year.toString()+":"+smonth+":"+sday;
	}
}

