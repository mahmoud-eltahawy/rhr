package com.rhr.heat;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.rhr.heat.enums.ShiftOrder;

public class GF {
	public static java.util.Date getDate(Integer year,Integer month, Integer day){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		//TODO:  why i should substract one !!!!
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
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
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			System.out.println(sdf.parse(str.substring(0, 5)));
			return new Time(sdf.parse(str.substring(0, 5)).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
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
