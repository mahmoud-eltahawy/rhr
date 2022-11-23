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
	
	@RequestMapping("/define")
	public ModelAndView definitions() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("definitionsPage");
		return mv;
	}
}
