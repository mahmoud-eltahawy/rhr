package com.rhr.heat.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rhr.heat.service.ShiftService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BasicController {
	private final ShiftService service;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("title","last week dynamicaly");
		model.addAttribute("week",service.pickLastWeeks(1));
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
