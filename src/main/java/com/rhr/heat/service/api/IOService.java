package com.rhr.heat.service.api;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rhr.heat.dao.topLayer.ShiftRepo;
import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.enums.ShiftOrder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IOService {
	private final ShiftRepo shiftRepo;

	public List<Shift> lastShift(int num) {
		return shiftRepo.findLast(num);
	}
	
	public List<Shift> after(Date date) {
		return shiftRepo.findRecent(date);
	}
	
	public List<Shift> day(Date date){
		return shiftRepo.findAll(date);
	}
	
	public List<Shift> shift(Date date,String order){
		return List.of(shiftRepo.findById(date,
				ShiftOrder.valueOf(order)).get());
	}
	
	public List<Shift> before(Date date) {
		return shiftRepo.findOlderThan(date);
	}
	
	public List<Shift> between(Date older, Date newer) {
		return shiftRepo.findBetween(older,newer);
	}
	
	public List<Shift> all() {
		return shiftRepo.findAll();
	}
	
	public int importShifts(String content) {
			List<Shift> shifts = new Gson().fromJson(content,
					new TypeToken<List<Shift>>() {}.getType());
			return shiftRepo.saveAll(shifts).size();
	}
}