package com.rhr.heat.service;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.model.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsertionService {
	private final ShiftRepo shiftIdRepo;
	
	public Shift saveShift(Shift shift) {
		shiftIdRepo.save(shift);
		return shift;
	}
}
