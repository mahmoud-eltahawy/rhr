package com.rhr.heat.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.topLayer.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchControllerDealer {
	private final Dealer service;

	public ModelAndView completeShift(ModelAndView mv,Shift shift) {
		Map<String, Map<Integer, List<ProblemDetail>>>	cats =
				service.getCategoryMachines(shift.getProblems());
		mv.addObject("theId",shift.getShiftId());
		if(!cats.isEmpty()) {
			mv.addObject("cats",cats);
		} else {
			mv.addObject("cats",null);
		}
		mv.addObject("flow",shift.getTotalFlowAverage());
		mv.addObject("temps",shift.getTemps());
		mv.addObject("notes",shift.getNotes());
		mv.addObject("names",shift.getEmployees().stream().map(e ->{
			return e.getFirstName()+" "+e.getMiddleName()+" "+e.getLastName();
		}).collect(Collectors.toList()));
		return mv;
	}
}
