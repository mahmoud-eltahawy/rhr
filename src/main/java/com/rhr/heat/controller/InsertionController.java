package com.rhr.heat.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.model.Shift;
import com.rhr.heat.service.InsertionService;

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
}
