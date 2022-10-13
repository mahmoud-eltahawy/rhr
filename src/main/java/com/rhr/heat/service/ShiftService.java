package com.rhr.heat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.model.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShiftService {
	public final ShiftRepo shiftRepo;
	
	public List<Shift> pickLastThree(){
		return shiftRepo.findLast(3);
	}
}
