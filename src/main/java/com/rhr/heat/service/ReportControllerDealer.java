package com.rhr.heat.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.topLayer.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportControllerDealer {
	private final Dealer service;
	private final ProblemRepo problemRepo;

	public ModelAndView completeShift(ModelAndView mv,Shift shift) {
		Map<String, Map<Integer, List<ProblemDetail>>>	cats =
				service.getCategoryMachines(shift.getProblems());
		mv.addObject("theId",shift.getShiftId());
		mv.addObject("catValue",new Gson().toJson(service.getStandardCategoryNums()));
		mv.addObject("problemsValue",new Gson().toJson(problemRepo.findAllTitles()));
		mv.addObject("pushable", shift.isPushable().isEmpty());
		if(!cats.isEmpty()) {
			mv.addObject("cats",cats);
		} else {
			mv.addObject("cats",null);
		}
		mv.addObject("flow",shift.getTotalFlowAverage());
		mv.addObject("temps",shift.getTemps());
		mv.addObject("notes",shift.getNotes());
		if(shift.getEmployees() != null) {
			mv.addObject("names",shift.getEmployees().stream().map(e ->{
				return e.getFirstName()+" "+e.getMiddleName()+" "+e.getLastName();
			}).collect(Collectors.toList()));
			List<Employee> ems = shift.getEmployees();
			if(ems != null) {
				mv.addObject("emps", ems.stream().map(e -> e.getUsername())
					.collect(Collectors.toList()));
			}
		}
		return mv;
	}
}
