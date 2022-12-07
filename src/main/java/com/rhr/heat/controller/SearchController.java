package com.rhr.heat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rhr.heat.entity.Machine;
import com.rhr.heat.service.SearchService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/show")
@RequiredArgsConstructor
public class SearchController {
	private final SearchService service;

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
			@RequestParam("machine")String machine,
			@RequestParam("page")Integer page) {
		if(page < 0) {
			page = 0;
		}
		Machine m = service.getMachine(machine).orElseThrow();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("showMachines");
		mv.addObject("machine",m);
		mv.addObject("machineProblems",
				service.pickLastMachineProblems(m.getId(), page));
		mv.addObject("next",page + 1);
		mv.addObject("prev",page - 1);
		return mv;
	}
}
