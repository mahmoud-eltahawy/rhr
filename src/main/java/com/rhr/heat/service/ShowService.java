package com.rhr.heat.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.model.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowService {
	private final ShiftRepo shiftRepo;
	
	public List<Shift> shiftsNewerThan(Date date){
		return shiftRepo.findRecent(date);
	}
	
	public List<Shift> shiftsOlderThan(Date date){
		return shiftRepo.findOlderThan(date);
	}
	
	public Shift getShift(Date date,String order){
		return shiftRepo.findById(date, ShiftOrder
				.valueOf(order)).get();
	}
	
	public List<Shift> shiftsBetween(Date date1,Date date2){
		return shiftRepo.findBetween(date1, date2);
	}
}
