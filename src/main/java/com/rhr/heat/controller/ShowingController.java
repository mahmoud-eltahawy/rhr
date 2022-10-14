package com.rhr.heat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rhr.heat.service.ShowingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/show")
@RequiredArgsConstructor
public class ShowingController {
	private final ShowingService showingService;

	@GetMapping("/shift")
	public String showShift(@RequestParam("id")Long id,Model model) {
		model.addAttribute("shift", showingService.getShift(id).get());
		return "showShift";
	}
}
