package com.rhr.heat.controller;


import java.sql.Date;
import java.util.Calendar;
import java.util.TreeMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rhr.heat.model.Day;
import com.rhr.heat.service.ShiftService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BasicController {
	private final ShiftService service;

	@GetMapping("/")
	public String index(Model model) {
		TreeMap<Date, Day> week = service.pickLastWeeks(1);
		model.addAttribute("title","Welcom to index page");
		model.addAttribute("week", week);
		Calendar cal = Calendar.getInstance();
		cal.setTime(week.firstKey());
		model.addAttribute("fy",cal.get(Calendar.YEAR));
		model.addAttribute("fm",cal.get(Calendar.MONTH));
		model.addAttribute("fd",cal.get(Calendar.DAY_OF_MONTH));
		cal.setTime(week.lastKey());
		model.addAttribute("ly",cal.get(Calendar.YEAR));
		model.addAttribute("lm",cal.get(Calendar.MONTH));
		model.addAttribute("ld",cal.get(Calendar.DAY_OF_MONTH));
		return "index";
	}
	
	@RequestMapping("/customError")
	public String customError(@RequestParam("message")String message,Model model) {
		model.addAttribute("message", message);
		return "errorPage";
	}
	
	@RequestMapping("/comming")
	public String commingSoon() {
		return "commingSoon";
	}
}
