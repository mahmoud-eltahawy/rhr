package com.rhr.heat.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.service.api.InsertionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/insert")
public class InsertionController {
	private final InsertionService insertionService;
	
	@PostMapping("/shift")
	public Shift insertShift(@RequestBody(required = true) Shift shift) {
		return insertionService.saveShift(shift);
	}
	
	@PostMapping("/emp")
	public Employee insertEmployee(@RequestBody(required = true) Employee emp) {
		return insertionService.register(emp);
	}
}