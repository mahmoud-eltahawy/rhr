package com.rhr.heat.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
		model.addAttribute("week",new TreeMap<>(service.pickLastWeeks(1)));
		return "index";
	}
	
	@RequestMapping("customError")
	public String customError(@RequestParam("message")String message,Model model) {
		model.addAttribute("message", message);
		return "errorPage";
	}
}
