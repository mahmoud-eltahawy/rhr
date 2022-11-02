package com.rhr.heat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
