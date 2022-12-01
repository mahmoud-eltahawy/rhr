package com.rhr.heat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

	@RequestMapping("/")
	public ModelAndView reportPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reportPage");
		return mv;
	}
}