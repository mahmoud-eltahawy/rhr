package com.rhr.heat;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import com.rhr.heat.enums.ShiftOrder;

public class GF {
	public static Boolean equals(Time one,Time two) {
		Calendar calOne = Calendar.getInstance();
		calOne.setTimeInMillis(one.getTime());
		Calendar calTwo = Calendar.getInstance();
		calTwo.setTimeInMillis(two.getTime());
		
		if(calOne.get(Calendar.HOUR) == calTwo.get(Calendar.HOUR) &
				calOne.get(Calendar.MINUTE) == calTwo.get(Calendar.MINUTE) &
				calOne.get(Calendar.SECOND) == calTwo.get(Calendar.SECOND) &
				calOne.get(Calendar.MILLISECOND) == calTwo.get(Calendar.MILLISECOND)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean equals(Date one,Date two) {
		Calendar calOne = Calendar.getInstance();
		calOne.setTimeInMillis(one.getTime());
		Calendar calTwo = Calendar.getInstance();
		calTwo.setTimeInMillis(two.getTime());
		
		if(calOne.get(Calendar.YEAR) == calTwo.get(Calendar.YEAR) &
				calOne.get(Calendar.MONTH) == calTwo.get(Calendar.MONTH) &
				calOne.get(Calendar.DAY_OF_MONTH) == calTwo.get(Calendar.DAY_OF_MONTH)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Time getTime(String str) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, Integer.parseInt(str.substring(0, 2)));
		cal.set(Calendar.MINUTE, Integer.parseInt(str.substring(3)));
		cal.set(Calendar.SECOND, 0);
		return new Time(cal.getTimeInMillis());
	}
	public static ShiftOrder next(ShiftOrder order) {
		if(order.equals(ShiftOrder.FIRST)) {
			return ShiftOrder.SECOND;
		} else if(order.equals(ShiftOrder.SECOND)) {
			return ShiftOrder.THIRD;
		} else if(order.equals(ShiftOrder.THIRD)) {
			return ShiftOrder.FIRST;
		} else {
			return null;
		}
	}
}
