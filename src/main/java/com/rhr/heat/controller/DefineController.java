package com.rhr.heat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rhr.heat.service.DefineService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/define")
public class DefineController{
	private final DefineService service;
	
	@RequestMapping("/")
	public ModelAndView definePage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("definitionsPage");
		mv.addObject("cats", service.noneHeadersCategories());
		return mv;
	}
	
	@RequestMapping("/category")
	public String defineCategory(
        @RequestParam("name")String name,
        @RequestParam("isHeader")Character isHeader) {

        if(isHeader == 't'){
            service.saveNewCategory(name, 0);
        } else {
            service.saveNewCategory(name, 1);
        }
		return "redirect:/define/";
	}
	
	@RequestMapping("/machine")
	public String addMachine(
        @RequestParam("category")String name) {
        
        service.addNewMachine(name);
		return "redirect:/define/";
	}
	
	@RequestMapping("/problem")
	public String addProblem(
        @RequestParam("name")String name,
        @RequestParam("desc")String description) {
        
        service.addNewProblem(name, description);
		return "redirect:/define/";
	}
}