package com.rhr.heat.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.model.Shift;
import com.rhr.heat.service.Tester;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {
	private final Tester tester;
	
	@RequestMapping("/ins")
	public String insert() {
		tester.insertData();
		return "inserted";
	}
	
	@RequestMapping("/get")
	public List<Shift> getAll() {
		return tester.getAllData();
	}
	
	@RequestMapping("/exp")
	public String exportToFile() {
		tester.exportAllToFile();
		return "exported";
	}
	
	@RequestMapping("/imp")
	public String importFromFile() {
		tester.importFromFile();
		return "exported";
	}
}
