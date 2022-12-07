package com.rhr.heat.service.api;

import java.sql.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.ShiftIdRepo;
import com.rhr.heat.dao.topLayer.ShiftRepo;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.model.Day;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowService {
	private final ShiftRepo shiftRepo;
	private final ShiftIdRepo shiftIdRepo;
	private final EmployeeRepo employeeRepo;
	
	public TreeMap<String ,Day> pickLastEmployeeSections(String username,Integer idx){
		return Day.getDays(employeeRepo.findHisLastShifts(username, idx * 7, 7));
	}

	public TreeMap<String ,Day> pickLastWeeks(Integer weekNum){
		return Day.getDays(shiftIdRepo.findFromTo(weekNum * 21, 21));
	}
	
	public TreeMap<String, Day> findDay(Date date) {
		List<ShiftId> shifts = shiftIdRepo.findAll(date);
		if(!shifts.isEmpty()) {
			return Day.getDays(shifts);
		} else {
			return null;
		}
	}
	
	public UUID getShiftId(Date date,ShiftOrder order){
		return shiftIdRepo.findIdById(date, order).orElse(null);
	}
	public List<Shift> shiftsNewerThan(Date date){
		return shiftRepo.findRecent(date);
	}
	
	public List<Shift> shiftsOlderThan(Date date){
		return shiftRepo.findOlderThan(date);
	}
	
	public List<Shift> getDay(Date date){
		return shiftRepo.findAll(date);
	}
	
	public Shift getShift(Date date,String order){
		return shiftRepo.findById(date, ShiftOrder
				.valueOf(order)).get();
	}
	
	public List<Shift> shiftsBetween(Date date1,Date date2){
		return shiftRepo.findBetween(date1, date2);
	}
}
