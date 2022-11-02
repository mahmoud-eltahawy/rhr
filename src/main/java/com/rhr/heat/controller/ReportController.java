package com.rhr.heat.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rhr.heat.Tools;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.enums.Machine;
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
		Shift shift = service.getCurrentShift();
		
		mv = Tools.completeShift(mv, shift);
		
		mv.addObject("allEmps", service.usernames());
		mv.addObject("emps", service.getCurrentShift()
				.getEmployees().stream().map(e -> e.getUsername())
				.collect(Collectors.toList()));
		mv.addObject("pTitles", service.problemsTitles());
		mv.addObject("pushable", shift.isPushable());
		mv.setViewName("reportPage");
		return mv;
	}

	@RequestMapping("/save")
	public String save() {
		service.save();
		return "redirect:/report/";
	}

	@PostMapping("/problem")
	public String problem(
			@RequestParam("machine")String machine,
			@RequestParam("problems")List<String> problems,
			@RequestParam("beginTime")String beginTime,
			@RequestParam("endTime")String endTime) {
		
		ProblemDetail pd = new ProblemDetail(null,
				problems.stream().map(p -> new Problem(p,null))
				.collect(Collectors.toList()),
				Machine.valueOf(machine),
				Tools.getTime(beginTime),
				Tools.getTime(endTime));
		service.addProblem(pd);
		return "redirect:/report/";
	}

	@PostMapping("/flow")
	public String flow(
			@RequestParam("machines")List<String> machines,
			@RequestParam("max")Integer max,
			@RequestParam("min")Integer min,
			@RequestParam("beginTime")String beginTime,
			@RequestParam("endTime")String endTime) {
		TotalFlow tf = new TotalFlow(null,
				machines.stream().map(m -> Machine.valueOf(m))
				.collect(Collectors.toList()),
				min, max,
				Tools.getTime(beginTime),
				Tools.getTime(endTime));
		service.addTotalFlow(tf);
		return "redirect:/report/";
	}

	@PostMapping("/emp")
	public String employee(@ModelAttribute("emp")String emp) {
		service.addEmployee(emp);
		return "redirect:/report/";
	}

	@PostMapping("/temp")
	public String temperature(
			@RequestParam("max")Integer max ,
			@RequestParam("min")Integer min) {
		service.setTemperature(max, min);
		return "redirect:/report/";
	}

	@PostMapping("/note")
	public String note(
			@RequestParam("note")String note) {
		service.setNote(note);
		return "redirect:/report/";
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