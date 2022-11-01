package com.rhr.heat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.service.ReportService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
	private final ReportService service;

	@RequestMapping("/")
	public ModelAndView reportPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reportPage");
		mv.addObject("emps", service.usernames());
		mv.addObject("pTitles", service.problemsTitles());
		mv.addObject("emp", new Employee());
		mv.addObject("problem", new ProblemDetail());
		mv.addObject("flow", new TotalFlow());
		mv.addObject("theId",service.getTheId());
		return mv;
	}

	@PostMapping("/problem")
	public ModelAndView problem(@ModelAttribute("problem")ProblemDetail pd) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reportPage");
		mv.addObject("pushable", service.addProblem(pd).isPushable());
		return mv;
	}

	@PostMapping("/flow")
	public ModelAndView flow(@ModelAttribute("flow")TotalFlow tf) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reportPage");
		mv.addObject("pushable", service.addTotalFlow(tf).isPushable());
		return mv;
	}

	@PostMapping("/emp")
	public ModelAndView employee(@ModelAttribute("emp")String emp) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reportPage");
		mv.addObject("pushable", service.addEmployee(emp).isPushable());
		return mv;
	}

	@PostMapping("/temp")
	public ModelAndView temperature(
			@RequestParam("max")Integer max ,
			@RequestParam("min")Integer min) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reportPage");
		mv.addObject("pushable", service.setTemperature(max, min).isPushable());
		return mv;
	}

	@PostMapping("/note")
	public ModelAndView note(
			@RequestParam("note")String note) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reportPage");
		mv.addObject("pushable", service.setNote(note).isPushable());
		return mv;
	}

	@PostMapping("/remove/problem")
	public ModelAndView removeProblem(@ModelAttribute("problem")ProblemDetail pd) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reportPage");
		mv.addObject("pushable", service.removeProblem(pd).isPushable());
		return mv;
	}

	@PostMapping("/remove/flow")
	public ModelAndView removeFlow(@ModelAttribute("flow")TotalFlow tf) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reportPage");
		mv.addObject("pushable", service.removeTotalFlow(tf).isPushable());
		return mv;
	}

	@PostMapping("/remove/emp")
	public ModelAndView removeEmployee(@ModelAttribute("emp")Employee emp) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reportPage");
		mv.addObject("pushable", service.removeEmployee(emp).isPushable());
		return mv;
	}
}