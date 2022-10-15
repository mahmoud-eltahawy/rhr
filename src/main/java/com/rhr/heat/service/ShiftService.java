package com.rhr.heat.service;

import java.sql.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.model.Day;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShiftService {
	public final ShiftRepo shiftRepo;
	
	public Map<Date ,Day> pickLastWeeks(Integer weeks){
		return Day.getDays(shiftRepo.findLast(weeks * 21,false));
	}
	
	public Map<Date ,Day> pickLastdays(Integer days){
		return Day.getDays(shiftRepo.findLast(days * 3,false));
	}
}
