package com.rhr.heat;

import static com.rhr.heat.enums.Machine.KILEN_ONE;
import static com.rhr.heat.enums.Machine.KILEN_TWO;
import static com.rhr.heat.enums.Machine.KILEN_THREE;
import static com.rhr.heat.enums.Machine.KILEN_FOUR;
import static com.rhr.heat.enums.Machine.KILEN_FIVE;
import static com.rhr.heat.enums.Machine.DRAYER_ONE;
import static com.rhr.heat.enums.Machine.DRAYER_TWO;
import static com.rhr.heat.enums.Machine.DRAYER_THREE;
import static com.rhr.heat.enums.Machine.DRAYER_FOUR;
import static com.rhr.heat.enums.Machine.DRAYER_FIVE;
import static com.rhr.heat.enums.Machine.DRAYER_SIX;
import static com.rhr.heat.enums.Machine.DRAYER_SEVEN;
import static com.rhr.heat.enums.Machine.ATM_ONE;
import static com.rhr.heat.enums.Machine.ATM_TWO;
import static com.rhr.heat.enums.Machine.PROJECT;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.enums.Machine;
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
	
	
	public static Map<Machine, List<ProblemDetail>> getMachinesProblems(List<ProblemDetail> allDetails){
		Map<Machine, List<ProblemDetail>> mp = new HashMap<>();
		for (ProblemDetail pd : allDetails) {
			if(mp.get(pd.getMachine()) == null) {
				List<ProblemDetail> pds = new ArrayList<>();
				pds.add(pd);
				mp.put(pd.getMachine(), pds);
			} else {
				List<ProblemDetail> pds = mp.get(pd.getMachine());
				pds.add(pd);
				mp.put(pd.getMachine(), pds);
			}
		}
		
		return mp;
	}

	public static ModelAndView completeShift(ModelAndView mv,Shift shift) {
		Map<Machine, List<ProblemDetail>> mp = getMachinesProblems(shift.getProblems());
		
		mv.addObject("theId",shift.getShiftId());
		mv.addObject("k1",mp.get(KILEN_ONE));
		mv.addObject("k2",mp.get(KILEN_TWO));
		mv.addObject("k3",mp.get(KILEN_THREE));
		mv.addObject("k4",mp.get(KILEN_FOUR));
		mv.addObject("k5",mp.get(KILEN_FIVE));
		mv.addObject("d1",mp.get(DRAYER_ONE));
		mv.addObject("d2",mp.get(DRAYER_TWO));
		mv.addObject("d3",mp.get(DRAYER_THREE));
		mv.addObject("d4",mp.get(DRAYER_FOUR));
		mv.addObject("d5",mp.get(DRAYER_FIVE));
		mv.addObject("d6",mp.get(DRAYER_SIX));
		mv.addObject("d7",mp.get(DRAYER_SEVEN));
		mv.addObject("a1",mp.get(ATM_ONE));
		mv.addObject("a2",mp.get(ATM_TWO));
		mv.addObject("p" ,mp.get(PROJECT));
		mv.addObject("flow",shift.getTotalFlowAverage());
		mv.addObject("maxT",shift.getMaxTemperature());
		mv.addObject("minT",shift.getMinTemperature());
		mv.addObject("note",shift.getExceptionalNote());
		mv.addObject("names",shift.getEmployees().stream().map(e ->{
			return e.getFirstName()+" "+e.getMiddleName()+" "+e.getLastName();
		}).collect(Collectors.toList()));
		return mv;
	}
}
