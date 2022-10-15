package com.rhr.heat.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.rhr.heat.entity.Shift;
import com.rhr.heat.service.ShiftService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BasicController {
	private final ShiftService service;

	@GetMapping("/")
	public String index(Model model) {
		List<Shift> lastDay = service.pickLastWeek();
		model.addAttribute("l1",lastDay.get(0));
		model.addAttribute("m1",lastDay.get(1));
		model.addAttribute("f1",lastDay.get(2));
		model.addAttribute("l2",lastDay.get(3));
		model.addAttribute("m2",lastDay.get(4));
		model.addAttribute("f2",lastDay.get(5));
		model.addAttribute("l3",lastDay.get(6));
		model.addAttribute("m3",lastDay.get(7));
		model.addAttribute("f3",lastDay.get(8));
		model.addAttribute("l4",lastDay.get(9));
		model.addAttribute("m4",lastDay.get(10));
		model.addAttribute("f4",lastDay.get(11));
		model.addAttribute("l5",lastDay.get(12));
		model.addAttribute("m5",lastDay.get(13));
		model.addAttribute("f5",lastDay.get(14));
		model.addAttribute("l6",lastDay.get(15));
		model.addAttribute("m6",lastDay.get(16));
		model.addAttribute("f6",lastDay.get(17));
		model.addAttribute("l7",lastDay.get(18));
		model.addAttribute("m7",lastDay.get(19));
		model.addAttribute("f7",lastDay.get(20));
		return "index";
	}
}
