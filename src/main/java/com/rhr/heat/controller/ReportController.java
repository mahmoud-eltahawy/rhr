package com.rhr.heat.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.service.ReportControllerDealer;
import com.rhr.heat.service.ReportService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
	private final ReportService service;
	private final ReportControllerDealer dealer;

	@RequestMapping("/")
	public ModelAndView reportPage() {
		ModelAndView mv = new ModelAndView();
		Shift shift = service.currenShift();
		mv = dealer.completeShift(mv, shift);
		mv.setViewName("reportPage");
		return mv;
	}
}