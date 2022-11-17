package com.rhr.heat.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.Temperature;
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
	public ModelAndView reportPage(
			@PathVariable(name = "message",required = false)String message) {
		ModelAndView mv = new ModelAndView();
		Shift shift = service.getCurrentShift();
		mv = dealer.completeShift(mv, shift);
		mv.addObject("message", message);
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
			@RequestParam("category")String category,
			@RequestParam("number")Integer number,
			@RequestParam("problems")List<String> problems,
			@RequestParam("beginTime")String beginTime,
			@RequestParam("endTime")String endTime) {
		
		return "redirect:/report/?message="+service
				.reportProblem(category, number, problems, beginTime, endTime);
	}

	@PostMapping("/flow")
	public String flow(
			@RequestParam("machines")List<String> machines,
			@RequestParam("max")Integer max,
			@RequestParam("min")Integer min,
			@RequestParam("beginTime")String beginTime,
			@RequestParam("endTime")String endTime) {
		return "redirect:/report/?message="
			+service.reportTotalFlow(machines, max, min, beginTime, endTime);
	}

	@PostMapping("/emp")
	public String employee(@ModelAttribute("emp")String emp) {
		service.addEmployee(emp);
		return "redirect:/report/";
	}

	@PostMapping("/temp")
	public String temperature(
			@RequestParam("max")Integer max ,
			@RequestParam("min")Integer min ,
			@RequestParam("machine")UUID id) {
		service.addTemp(new Temperature(null, null, new Machine(id, null, null), max, min));
		return "redirect:/report/";
	}

	@PostMapping("/note")
	public String note(
			@RequestParam("note")String note) {
		service.addNote(new Note(null, null, note));
		return "redirect:/report/";
	}

	@RequestMapping("/remove/machine/problems")
	public String removeMachineProblems(
			@RequestParam("cat")String cat,
			@RequestParam("num")Integer num) {
		return "redirect:/report/?message="+service.removeMachineProblems(cat,num);
	}

	@RequestMapping("/remove/all/flow")
	public String removeAllFlow() {
		service.removeAllFlow();
		return "redirect:/report/?message=done";
	}

	@RequestMapping("/remove/flow")
	public String removeFlow(@RequestParam("id")UUID id) {
		return "redirect:/report/?message="+service.removeFlow(id);
	}

	@RequestMapping("/add/problem/problems")
	public String addProblemProblems(
			@RequestParam("id")UUID id,
			@RequestParam("titles")List<String> titles) {
		return "redirect:/report/?message="+service.addProblemProblems(id,titles);
	}

	@RequestMapping("/remove/problem/problem")
	public String removeProblemProblem(
			@RequestParam("id")UUID id,
			@RequestParam("title")String title) {
		return "redirect:/report/?message="+service.removeProblemProblem(id,title);
	}

	@PostMapping("/remove/problem")
	public String removeProblem(@RequestParam("id")UUID id) {
		return "redirect:/report/?message="+service.removeProblem(id);
	}

	@PostMapping("/remove/emp")
	public ModelAndView removeEmployee(@ModelAttribute("emp")Employee emp) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reportPage");
		mv.addObject("pushable", service.removeEmployee(emp).isPushable());
		return mv;
	}
}