package com.rhr.heat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class BinderController {

	@RequestMapping("/")
	public String dashboard() {
		return "/html/dashboard.html";
	}
	@RequestMapping("/search")
	public String searchPage() {
		return "/html/searchButtons.html";
	}
	@RequestMapping("/comming")
	public String commingSoon() {
		return "/html/commingSoon.html";
	}
	@RequestMapping("/myerror")
	public String error() {
		return "/html/errorPage.html";
	}
	@RequestMapping("/report")
	public String reportPage() {
		return "/html/reportPage.html";
	}
	@RequestMapping("/show/shift")
	public String showShift() {
		return "/html/showShift.html";
	}
	@RequestMapping("/define/page")
	public String definePage() {
		return "/html/definitionsPage.html";
	}
	@RequestMapping("/show/last/week")
	public String lastWeek() {
		return "/html/showDays.html";
	}
}