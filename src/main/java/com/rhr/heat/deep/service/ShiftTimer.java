package com.rhr.heat.deep.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.rhr.heat.GF;
import com.rhr.heat.entity.Identity;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.enums.ShiftOrder;

@Component
public class ShiftTimer {
	
	public Boolean isTimeSuitable() {
		Timestamp last15 = shiftBegin(GF.next(currentShiftId().getShift()));
		Timestamp end = new Timestamp(last15.getTime() + TimeUnit.MINUTES.toMillis(15));
		Timestamp begin45 = new Timestamp(last15.getTime() - TimeUnit.MINUTES.toMillis(30));
		Timestamp now = workNow();
		if(now.getTime() >= begin45.getTime() && now.getTime() <= end.getTime()) {
			return true;
		}
		return false;
	}
	
	public ShiftId currentShiftId() {
		var now = workNow();
		ShiftId id = new ShiftId(UUID.randomUUID());
		id.setDate(new Date(workNow().getTime()));
		
	   if(now.after(shiftBegin(ShiftOrder.FIRST)) &
				now.before(shiftBegin(ShiftOrder.SECOND))) {
				   id.setShift(ShiftOrder.FIRST);
		} else if(now.after(shiftBegin(ShiftOrder.SECOND)) &
				now.before(shiftBegin(ShiftOrder.THIRD))) {
				   id.setShift(ShiftOrder.SECOND);
		} else if(now.after(shiftBegin(ShiftOrder.THIRD))) {
				   id.setShift(ShiftOrder.THIRD);
		}
		return id;
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
