package com.rhr.heat.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.model.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowingService {
	private final ShiftRepo shiftRepo;
	
	public Optional<Shift> getShift(Long id) {
		return shiftRepo.findById(id, true);
	}

}