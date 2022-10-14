package com.rhr.heat.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.enums.ShiftOrder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowService {
	private final ShiftRepo shiftRepo;
	
	public List<Shift> shiftsNewerThan(Date date){
		return shiftRepo.findRecent(date,false);
	}
	
	public List<Shift> shiftsOlderThan(Date date){
		return shiftRepo.findOlderThan(date,false);
	}
	
	public List<Shift> getDay(Date date){
		return shiftRepo.findAll(date,false);
	}
	
	public Shift getShift(Date date,String order){
		return shiftRepo.findById(date, ShiftOrder
				.valueOf(order),false).get();
	}
	
	public List<Shift> shiftsBetween(Date date1,Date date2){
		return shiftRepo.findBetween(date1, date2,false);
	}
}
