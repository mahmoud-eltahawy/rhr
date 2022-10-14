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
		List<Shift> lastDay = service.pickLastThree();
		model.addAttribute("last",lastDay.get(0));
		model.addAttribute("middle",lastDay.get(1));
		model.addAttribute("first",lastDay.get(2));
		return "index";
	}
}
