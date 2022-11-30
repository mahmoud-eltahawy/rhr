package com.rhr.heat.service;

import org.springframework.stereotype.Service;

import com.rhr.heat.components.ReportComponent;
import com.rhr.heat.dao.topLayer.ShiftRepo;
import com.rhr.heat.entity.topLayer.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final ReportComponent component;
	private final ShiftRepo shiftRepo;
	
	public Shift currenShift(){
		return shiftRepo.fullFill(component.getCurrentShift());
	}
}