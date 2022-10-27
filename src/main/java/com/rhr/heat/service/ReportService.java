package com.rhr.heat.service;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.enums.ShiftOrder;

@Service
public class ReportService {
	
	public ShiftId thisShift() {
		Calendar cal = Calendar.getInstance();
		Time now = new Time(cal.getTime().getTime());
		
		Date shiftDate = null;
		ShiftOrder shiftOrder = null;
		
		if(now.after(BeginOrEnd(ShiftOrder.FIRST, true)) &
				now.before(BeginOrEnd(ShiftOrder.FIRST, false))) {
			shiftDate = new Date(cal.getTime().getTime());
			shiftOrder = ShiftOrder.FIRST;
		} else if(now.after(BeginOrEnd(ShiftOrder.SECOND, true)) &
				now.before(BeginOrEnd(ShiftOrder.SECOND, false))) {
			shiftDate = new Date(cal.getTime().getTime());
			shiftOrder = ShiftOrder.SECOND;
		} else if(now.after(BeginOrEnd(ShiftOrder.THIRD, true)) &
				now.before(BeginOrEnd(ShiftOrder.THIRD, false))) {
			shiftDate = new Date(cal.getTime().getTime() - TimeUnit.DAYS.toMillis(1));
			shiftOrder = ShiftOrder.THIRD;
		}
		
		
		return new ShiftId(null, shiftDate, shiftOrder);
	}

	public Date BeginOrEnd(ShiftOrder order , Boolean begin) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MINUTE, 15);
		cal.set(Calendar.SECOND, 0);
		if(begin) {
			if(order == ShiftOrder.FIRST) {
				cal.set(Calendar.HOUR, 8);
				cal.set(Calendar.AM_PM, Calendar.AM);
				return new Date(cal.getTime().getTime());
			} else if(order == ShiftOrder.SECOND) {
					return BeginOrEnd(ShiftOrder.FIRST, false);
			} else if(order == ShiftOrder.THIRD) {
					return BeginOrEnd(ShiftOrder.SECOND, false);
			} else {
				return null;
			}
		} else {
			if(order == ShiftOrder.FIRST) {
				cal.set(Calendar.HOUR, 4);
				cal.set(Calendar.AM_PM, Calendar.PM);
				return new Date(cal.getTime().getTime());
			} else if(order == ShiftOrder.SECOND) {
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.AM_PM, Calendar.AM);
				return new Date(cal.getTime().getTime());
			} else if(order == ShiftOrder.THIRD) {
				return BeginOrEnd(ShiftOrder.FIRST, true);
			} else {
				return null;
			}
		}
	}
}
