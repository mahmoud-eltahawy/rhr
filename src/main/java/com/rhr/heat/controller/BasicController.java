package com.rhr.heat.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.model.StringModel;
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
		mv.addObject("next", 1);
		mv.addObject("prev", 0);
		mv.addObject("week",service.pickLastWeeks(0));
		return mv;
	}

	@GetMapping("/searchPage")
	public ModelAndView searchPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("searchButtons");
		mv.addObject("HModel", new StringModel());
		mv.addObject("names", service.usernames());
		mv.addObject("pTitles", service.problemsTitles());
		return mv;
	}

	@GetMapping("/report")
	public ModelAndView reportPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reportPage");
		mv.addObject("emps", service.usernames());
		mv.addObject("pTitles", service.problemsTitles());
		mv.addObject("emp", new Employee());
		mv.addObject("problem", new ProblemDetail());
		mv.addObject("flow", new TotalFlow());
		return mv;
	}
}