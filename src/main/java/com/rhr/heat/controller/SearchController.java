package com.rhr.heat.controller;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.service.CommonService;
import com.rhr.heat.service.SearchService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/show")
@RequiredArgsConstructor
public class SearchController {
	private final SearchService service;
	private final CommonService commonService;

	@GetMapping("/")
	public ModelAndView searchPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("searchButtons");
		mv.addObject("names", service.usernames());
		mv.addObject("pTitles", service.problemsTitles());
		mv.addObject("machines",service.allmachines());
		return mv;
	}

	@GetMapping("/last/week")
	public ModelAndView lastWeek() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("showDays");
		mv.addObject("title","Welcom to index page");
		mv.addObject("next", 1);
		mv.addObject("prev", 0);
		mv.addObject("week",service.pickLastWeeks(0));
		return mv;
	}

	@GetMapping("/shift")
	public ModelAndView showShift(@RequestParam("id")UUID id) {
		ModelAndView mv = new ModelAndView();
		Optional<Shift> s =  service.getShift(id);
		if(s.isPresent()) {
			Shift shift =  s.get();
			
			mv.setViewName("showShift");
			mv = commonService.completeShift(mv, shift);
			
			
		} else {
			mv.setViewName("errorPage");
			mv.addObject("message","this shift is not recorded");
		}
		return mv;
	}
	
	@GetMapping("/shft")
	public ModelAndView showShift(
			@RequestParam("date")
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			Date date,
			@RequestParam("order")
			String order) {
		ModelAndView mv = new ModelAndView();
		Optional<Shift> s =  service.getShift(new java.sql.Date(date.getTime()),ShiftOrder.valueOf(order));
		if(s.isPresent()) {
			Shift shift =  s.get();
			
			mv.setViewName("showShift");
			mv = commonService.completeShift(mv, shift);
			
		} else {
			mv.setViewName("errorPage");
			mv.addObject("message","this shift is not recorded");
		}
		return mv;
	}

	@GetMapping("/week")
	public ModelAndView showWeek(@RequestParam("week")Integer week) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("showDays");
		mv.addObject("title","last week page");
		mv.addObject("next",week + 1);
		if(week > 0) {
			mv.addObject("prev",week - 1);
		} else {
			mv.addObject("prev",week);
		}
		mv.addObject("week",service.pickLastWeeks(week));
		return mv;
	}

	@GetMapping("/day")
	public ModelAndView showWeek(@RequestParam("date")
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date date) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("showDay");
		mv.addObject("title",date);
		mv.addObject("week",service.findDay(new java.sql.Date(date.getTime())));
		return mv;
	}

	@PostMapping("/emp")
	public ModelAndView showEmp(
			@RequestParam("month")Integer month,
			@ModelAttribute("username")String username) {
		System.out.println(month +" " + username);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("employeeDays");
		mv.addObject("title","Employee review");
		Employee emp = service.getEmployee(username).get();
		mv.addObject("fullname",emp.getFirstName()+" "+emp.getMiddleName()+" "+emp.getLastName());
		mv.addObject("position",emp.getPosition());
		mv.addObject("next",month + 1);
		if(month > 0) {
			mv.addObject("prev",month - 1);
		} else {
			mv.addObject("prev",month);
		}
		mv.addObject("username", username);
		mv.addObject("months",service.pickLastEmployeeSections(username, month));
		return mv;
	}

	@PostMapping("/problem")
	public ModelAndView showProblem(@RequestParam("problem-title")String problem,
			@RequestParam("page")Integer page) {
		if(page < 0) {
			page = 0;
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("showProblems");
		mv.addObject("problem", service.findProblem(problem));
		mv.addObject("problems",service.pickLastProblems(problem, page));
		mv.addObject("next",page + 1);
		mv.addObject("prev",page - 1);
		return mv;
	}

	@PostMapping("/machine")
	public ModelAndView showMachine(
			@RequestParam("machine-id")UUID id,
			@RequestParam("page")Integer page) {
		if(page < 0) {
			page = 0;
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("showMachines");
		mv.addObject("machine",service.getMachine(id).get());
		mv.addObject("machineProblems",
				service.pickLastMachineProblems(id, page));
		mv.addObject("next",page + 1);
		mv.addObject("prev",page - 1);
		return mv;
	}
}
