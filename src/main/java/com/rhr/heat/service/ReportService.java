package com.rhr.heat.service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.enums.ShiftOrder;

@Service
public class ReportService {
	
	public ShiftId thisShift(Calendar cal) {
		cal = check(cal);
		Timestamp now = new Timestamp(cal.getTimeInMillis());

		if(now.after(BeginOrEnd(ShiftOrder.THIRD, false)) &
				now.before(BeginOrEnd(ShiftOrder.SECOND, true))) {
			return new ShiftId(null,
					new Date(cal.getTimeInMillis()),
					ShiftOrder.FIRST);
		} else if(now.after(BeginOrEnd(ShiftOrder.FIRST, false)) &
				now.before(BeginOrEnd(ShiftOrder.THIRD, true))) {
			Date date = null;
			if(cal.get(Calendar.AM_PM) == Calendar.AM &
					cal.get(Calendar.HOUR) == 0 &
					cal.get(Calendar.MINUTE) < 15 &
					cal.get(Calendar.SECOND) < 60 &
					cal.get(Calendar.MILLISECOND) < 1000) {
				date = new Date(cal.getTimeInMillis() - TimeUnit.DAYS.toMillis(1));
			} else {
				date = new Date(cal.getTimeInMillis());
			}
			return new ShiftId(null, date, ShiftOrder.SECOND);
		} else if(now.before(BeginOrEnd(ShiftOrder.FIRST, true))) {
			return new ShiftId(null,
					new Date(cal.getTimeInMillis() - TimeUnit.DAYS.toMillis(1)),
					ShiftOrder.THIRD);
		} else {
			return null;
		}
	}

	public Timestamp BeginOrEnd(ShiftOrder order , Boolean begin) {
		Calendar cal = Calendar.getInstance();
		
		cal = check(cal);
		
		cal.set(Calendar.HOUR,         8);
		cal.set(Calendar.MINUTE,       14);
		cal.set(Calendar.SECOND,       59);
		cal.set(Calendar.MILLISECOND,  999);
		cal.set(Calendar.AM_PM,        Calendar.AM);
		if(begin) {
			       if(order == ShiftOrder.FIRST) {
				return new Timestamp(cal.getTimeInMillis());
			} else if(order == ShiftOrder.SECOND) {
				return new Timestamp(cal.getTimeInMillis() + TimeUnit.HOURS.toMillis(8));
			} else if(order == ShiftOrder.THIRD) {
				return new Timestamp(cal.getTimeInMillis() + TimeUnit.HOURS.toMillis(16));
			} else {
				return null;
			}
		} else {
				   if(order == ShiftOrder.FIRST) {
				return BeginOrEnd(ShiftOrder.SECOND, true);
			} else if(order == ShiftOrder.SECOND) {
				return BeginOrEnd(ShiftOrder.THIRD, true);
			} else if(order == ShiftOrder.THIRD) {
				return BeginOrEnd(ShiftOrder.FIRST, true);
			} else {
				return null;
			}
		}
	}
	
	private Calendar check(Calendar cal) {
		if(cal.get(Calendar.HOUR) < 8 &
				cal.get(Calendar.MINUTE) < 15 &
				cal.get(Calendar.SECOND) < 60 &
				cal.get(Calendar.MILLISECOND) < 1000 &
				cal.get(Calendar.AM_PM) == Calendar.AM) {
			cal.setTime(new Timestamp(cal.getTimeInMillis() - TimeUnit.DAYS.toMillis(1)));
		}
		return cal;
	}
}
