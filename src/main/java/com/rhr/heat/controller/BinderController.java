package com.rhr.heat.controller;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;
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

	@RequestMapping("/report/")
	public String reportPage() {
		return "reportPage";
	}

	@RequestMapping("/show/shift")
	public String showShift() {
		return "showShift";
	}
	
	@RequestMapping("/show/shft")
	public String showShift(
			@RequestParam("date")
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			Date date,
			@RequestParam("order")
			String order) {
		return "showShift";
	}
}