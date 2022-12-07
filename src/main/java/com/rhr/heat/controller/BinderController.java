package com.rhr.heat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BinderController {

	@RequestMapping("/")
	public String dashboard() {
		return "dashboard";
	}
	@RequestMapping("/search")
	public String searchPage() {
		return "searchButtons";
	}
	@RequestMapping("/comming")
	public String commingSoon() {
		return "commingSoon";
	}
	@RequestMapping("/myerror")
	public ModelAndView error(@RequestParam("message")String message) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("errorPage");
		mv.addObject("message",message);
		return mv;
	}
	@RequestMapping("/report")
	public String reportPage() {
		return "reportPage";
	}
	@RequestMapping("/show/shift")
	public String showShift() {
		return "showShift";
	}
	@RequestMapping("/define/page")
	public String definePage() {
		return "definitionsPage";
	}
	@RequestMapping("/show/last/week")
	public String lastWeek() {
		return "showDays";
	}
}