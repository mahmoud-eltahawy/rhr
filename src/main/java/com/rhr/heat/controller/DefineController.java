package com.rhr.heat.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.entity.Category;
import com.rhr.heat.service.DefineService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/define")
public class DefineController{
	private final DefineService service;
	@GetMapping("/get/all/categories")
	public List<String> getAllCategories(){
		return service.hasMachinesCategoriesNames();
	}
	@RequestMapping("/category")
	public Boolean defineCategory(
        @RequestParam("name")String name,
        @RequestParam("hasTemperature")Boolean ht,
        @RequestParam("hasMachines")Boolean hm) {
		return service.saveNewCategory(new Category(name, hm, ht));
	}
	@RequestMapping("/machine")
	public Integer addMachine(
        @RequestParam("category")String name) {
        return service.addNewMachine(name);
	}
	@RequestMapping("/problem")
	public Boolean addProblem(
        @RequestParam("name")String name,
        @RequestParam("desc")String description) {
     	return service.addNewProblem(name, description);
	}
}