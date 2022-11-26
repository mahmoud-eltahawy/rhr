package com.rhr.heat.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.service.ReportControllerDealer;
import com.rhr.heat.service.ReportService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
	private final ReportService service;
	private final ReportControllerDealer dealer;

	@RequestMapping("/")
	public ModelAndView reportPage(
			@PathVariable(name = "message",required = false)String message) {
		ModelAndView mv = new ModelAndView();
		Shift shift = service.currenShift();
		mv = dealer.completeShift(mv, shift);
		mv.addObject("message", message);
		mv.setViewName("reportPage");
		return mv;
	}

	@PostMapping("/flow")
	public String flow(
			@RequestParam("machines")List<String> machines,
			@RequestParam("max")Integer max,
			@RequestParam("min")Integer min,
			@RequestParam("beginTime")String beginTime,
			@RequestParam("endTime")String endTime) {
		return "redirect:/report/?message="
			+service.reportTotalFlow(machines, max, min, beginTime, endTime);
	}

	@RequestMapping("/remove/flow")
	public String removeFlow(@RequestParam("id")UUID id) {
		return "redirect:/report/?message="+service.removeFlow(id);
	}

	@RequestMapping("/remove/all/flow")
	public String removeAllFlow() {
		service.removeAllFlow();
		return "redirect:/report/?message=all total flow records removed";
	}

	@RequestMapping("/remove/flow/machine")
	public String removeFlowMachine(
			@RequestParam("fid")UUID flowId,
			@RequestParam("machine")String machine) {
		return "redirect:/report/?message="+service.removeFlowMachine(flowId,machine);
	}

	@RequestMapping("/add/flow/machines")
	public String addFlowMachines(
			@RequestParam("id")UUID flowId,
			@RequestParam("machines")List<String> machines) {
		return "redirect:/report/?message="+service.addFlowMachines(flowId,machines);
	}

	@PostMapping("/temp")
	public String temperature(
			@RequestParam("max")Integer max ,
			@RequestParam("min")Integer min ,
			@RequestParam("machine")String machine) {
		
		return "redirect:/report/?message="+service.reportTemperature(machine,max,min);
	}

	@RequestMapping("/remove/temp")
	public String removeTemp(@RequestParam("id")UUID id) {
		return "redirect:/report/?message="+service.removeTemp(id);
	}

	@RequestMapping("/remove/all/temp")
	public String removeAllTemp() {
		service.removeAllTemp();
		return "redirect:/report/?message=all temperature records removed";
	}

	@PostMapping("/note")
	public String note(
			@RequestParam("note")String note) {
		service.reportNote(note);
		return "redirect:/report/?message=a note was added successfully";
	}

	@RequestMapping("/remove/note")
	public String removeNote(
			@RequestParam("note")String note) {
		service.removeNote(note);
		return "redirect:/report/?message=a note was removed successfully";
	}

	@RequestMapping("/remove/all/note")
	public String removeAllNote() {
		service.removeAllNote();
		return "redirect:/report/?message=all note records removed";
	}

	@PostMapping("/emp")
	public String employee(@ModelAttribute("emp")String emp) {
		return "redirect:/report/?message="+service.reportEmployee(emp);
	}

	@RequestMapping("/remove/emp")
	public String removeEmployee(@RequestParam("id")UUID id) {
		return "redirect:/report/?message="+service.removeEmployee(id);
	}

	@RequestMapping("/remove/all/emp")
	public String removeAllEmp() {
		service.removeAllEmp();
		return "redirect:/report/?message=all employees removed";
	}
}