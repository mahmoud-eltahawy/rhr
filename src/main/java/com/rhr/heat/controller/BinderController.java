package com.rhr.heat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BinderController {

	@GetMapping("/searchPage")
	public String searchPage() {
		return "searchButtons";
	}
	
	@GetMapping("/comming")
	public String commingSoon() {
		return "commingSoon";
	}
}
