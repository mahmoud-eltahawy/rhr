package com.rhr.heat;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.enums.ShiftOrder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Tools {
	private final Map<String, File> dataFiles;
	
	public Shift getCurrentShift() {
		Shift currentShift = null;
		try {
			FileReader fr = new FileReader(dataFiles.get("currentShift"));
			Gson gson = new Gson();
			currentShift = gson.fromJson(fr, Shift.class);
			if(replaceShift(currentShift)) {
				FileWriter fw = new FileWriter(dataFiles.get("currentShift"));
				currentShift = new Shift();
				currentShift.setShiftId(thisShift());
				gson.toJson(currentShift,fw);
				fw.close();
			}
			//TODO what if the id is different
			fr.close();
		} catch (JsonSyntaxException | JsonIOException  | IOException e) {
			e.printStackTrace();
		}
		return currentShift;
	}
	public Boolean replaceShift(Shift currentShift) {
		if(currentShift == null )  {
			return true;
		} 
		if(!currentShift.getShiftId().equals(thisShift())) {
			return true;
		}
		return false;
	}
	
	public ShiftId thisShift() {
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
	
	public void writeShift(Shift shift) {
		try {
			FileWriter fw = new FileWriter(dataFiles.get("currentShift"));
			new Gson().toJson(shift,fw);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public <T> Boolean exists(T comparable ,List<T> comparables){
		Boolean exists = false;
		for (T c : comparables) {
			if(c.equals(comparable)) {
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	public <T> List<T> addTo(T element ,List<T> list){
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
	
	public <T> List<T> removeFrom(T element ,List<T> list){
		if(list != null) {
			Iterator<T> it = list.iterator();
			while(it.hasNext()) {
				if(it.next().equals(element)) {
					it.remove();
				}
			}
		}	
		return list;
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
}
