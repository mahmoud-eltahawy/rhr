package com.rhr.heat.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rhr.heat.service.ShiftService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BasicController {
	private final ShiftService service;

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("showDays");
		mv.addObject("title","Welcom to index page");
		mv.addObject("week",service.pickLastWeeks(1));
		return mv;
	}
	
	@RequestMapping("/comming")
	public String commingSoon() {
		return "commingSoon";
	}
}