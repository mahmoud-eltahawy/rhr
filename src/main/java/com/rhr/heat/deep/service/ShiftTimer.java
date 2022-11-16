package com.rhr.heat.deep.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.rhr.heat.entity.Identity;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.enums.ShiftOrder;

@Component
public class ShiftTimer {
	
	public ShiftId currentShiftId() {
			   if(workNow().after(shiftBegin(ShiftOrder.FIRST)) &
				workNow().before(shiftBegin(ShiftOrder.SECOND))) {
			return new ShiftId(null, new Date(workNow().getTime()), ShiftOrder.FIRST);
		} else if(workNow().after(shiftBegin(ShiftOrder.SECOND)) &
				workNow().before(shiftBegin(ShiftOrder.THIRD))) {
			return new ShiftId(null, new Date(workNow().getTime()), ShiftOrder.SECOND);
		} else if(workNow().after(shiftBegin(ShiftOrder.THIRD)) &
				workNow().before(shiftBegin(null))) {
			return new ShiftId(null, new Date(workNow().getTime()), ShiftOrder.THIRD);
		}
		return null;
	}
	
	public Timestamp shiftBegin(ShiftOrder order) {
		Calendar cal = Calendar.getInstance(); 
		cal.setTimeInMillis(workNow().getTime());
			   if(order == ShiftOrder.FIRST) {
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 1);
			cal.set(Calendar.AM_PM, Calendar.AM);
			return new Timestamp(cal.getTimeInMillis());
		} else if(order == ShiftOrder.SECOND) {
			cal.set(Calendar.HOUR, 8);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 1);
			cal.set(Calendar.AM_PM, Calendar.AM);
			return new Timestamp(cal.getTimeInMillis());
		} else if(order == ShiftOrder.THIRD) {
			cal.set(Calendar.HOUR, 4);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 1);
			cal.set(Calendar.AM_PM, Calendar.PM);
			return new Timestamp(cal.getTimeInMillis());
		} else {
			cal.set(Calendar.HOUR, 11);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			cal.set(Calendar.AM_PM, Calendar.PM);
			return new Timestamp(cal.getTimeInMillis());
		}
	}
	
	public Timestamp workNow() {
		Calendar cal = Calendar.getInstance();
		Timestamp now = new Timestamp(
				cal.getTimeInMillis() -
				TimeUnit.HOURS.toMillis(8) -
				TimeUnit.MINUTES.toMillis(15));
		return now;
	}
	
	public <T extends Identity> Boolean exists(T comparable ,List<T> comparables){
		for (T c : comparables) {
			if(c.getId().equals(comparable.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public <T extends Identity> List<T> addTo(T element ,List<T> list){
		if(list == null) {
			list =new ArrayList<>();
			list.add(element);
		} else {
			if(!exists(element, list)) {
				list.add(element);
			}
		}
		return list;
	}
	
	public <T extends Identity> List<T> removeFrom(T element ,List<T> list){
		if(list != null) {
			Iterator<T> it = list.iterator();
			while(it.hasNext()) {
				if(it.next().getId().equals(element.getId())) {
					it.remove();
				}
			}
		}	
		return list;
	}
}
