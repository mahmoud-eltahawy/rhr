package com.rhr.heat.service;


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

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.enums.ShiftOrder;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ReportService {
	private final Map<String, File> dataFiles;
	public Shift removeTotalFlow(TotalFlow totalFlow) {
		Shift oldShift = getCurrentShift();
		List<TotalFlow> tfs = oldShift.getTotalFlowAverage();
		if(tfs != null) {
			Iterator<TotalFlow> it = tfs.iterator();
			while(it.hasNext()) {
				TotalFlow c = it.next();
				if(ReportService.timeEquals(c.getCaseBeginTime(), totalFlow.getCaseBeginTime()) &
						ReportService.timeEquals(c.getCaseEndTime(), totalFlow.getCaseEndTime())) {
					it.remove();
				}
			}
		}	
		
		oldShift.setTotalFlowAverage(tfs);
		writeShift(oldShift);
		return oldShift;
	}
	
	public Shift removeEmployee(Employee employee) {
		Shift oldShift = getCurrentShift();
		List<Employee> ems = oldShift.getEmployees();
		if(ems != null) {
			Iterator<Employee> it = ems.iterator();
			while(it.hasNext()) {
				if(it.next().getUsername().equals(employee.getUsername())) {
					it.remove();
				}
			}
		}	
		oldShift.setEmployees(ems);
		writeShift(oldShift);
		return oldShift;
	}
	
	public Shift addProblem(ProblemDetail problemDetail) {
		Shift oldShift = getCurrentShift();
		List<ProblemDetail> pds = oldShift.getProblems();
		if(pds == null) {
			pds =new ArrayList<>();
			pds.add(problemDetail);
			oldShift.setProblems(pds);
		} else {
			Boolean exists = false;
			for (ProblemDetail pd : pds) {
				if(pd.equals(problemDetail)) {
					exists = true;
					break;
				}
			}
			if(!exists) {
				oldShift.getProblems().add(problemDetail);
			}
		}
		
		writeShift(oldShift);
		return oldShift;
	}
	
	public Shift addTotalFlow(TotalFlow totalFlow) {
		Shift oldShift = getCurrentShift();
		List<TotalFlow> tfs = oldShift.getTotalFlowAverage();
		if(tfs == null) {
			tfs =new ArrayList<>();
			tfs.add(totalFlow);
			oldShift.setTotalFlowAverage(tfs);
		} else {
			Boolean exists = false;
			for (TotalFlow tf : tfs) {
				if(tf.getCaseBeginTime().equals(totalFlow.getCaseBeginTime()) &
						tf.getCaseEndTime().equals(totalFlow.getCaseEndTime())) {
					exists = true;
					break;
				}
			}
			if(!exists) {
				oldShift.getTotalFlowAverage().add(totalFlow);
			}
		}
		
		writeShift(oldShift);
		return oldShift;
	}
	
	public Shift addEmployee(Employee employee) {
		Shift oldShift = getCurrentShift();
		List<Employee> ems = oldShift.getEmployees();
		if(ems == null) {
			ems =new ArrayList<>();
			ems.add(employee);
			oldShift.setEmployees(ems);
		} else {
			Boolean exists = false;
			for (Employee e : ems) {
				if(e.getUsername().equals(employee.getUsername())) {
					exists = true;
					break;
				}
			}
			if(!exists) {
				oldShift.getEmployees().add(employee);
			}
		}
		
		writeShift(oldShift);
		return oldShift;
	}
	
	public Shift stashShift(Shift newShift) {
		Shift oldShift = getCurrentShift();
		oldShift.setProblems(newShift.getProblems());
		oldShift.setTotalFlowAverage(newShift.getTotalFlowAverage());
		oldShift.setEmployees(newShift.getEmployees());
		oldShift.setExceptionalNote(newShift.getExceptionalNote());
		oldShift.setMinTemperature(newShift.getMinTemperature());
		oldShift.setMaxTemperature(newShift.getMaxTemperature());
		
		writeShift(oldShift);
		return oldShift;
	}
	
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
			fr.close();
		} catch (JsonSyntaxException | JsonIOException  | IOException e) {
			e.printStackTrace();
		}
		return currentShift;
	}
	
	private Boolean replaceShift(Shift currentShift) {
		if(currentShift == null )  {
			return true;
		} 
		if(currentShift.getShiftId().equals(thisShift())) {
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
	
	public static Boolean timeEquals(Time one,Time two) {
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
}
