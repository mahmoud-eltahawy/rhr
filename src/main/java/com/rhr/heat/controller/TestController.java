package com.rhr.heat.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.model.WorkDay;
import com.rhr.heat.service.Tester;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {
	private final Tester tester;
	
	@GetMapping("/test0")
	public String test0() {
		tester.insertday();
		return "sucess";
	}
	
	@GetMapping("/test1")
	public List<WorkDay> test1() {
		return tester.getAllDays();
	}
}
