package com.rhr.heat.service;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.deep.service.ShiftTimer;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.enums.Pushable;
import com.rhr.heat.model.EmployeeName;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportControllerDealer {
	private final Dealer service;
	private final EmployeeRepo employeeRepo;
	private final ProblemRepo problemRepo;
	private final ShiftTimer timer;

	public ModelAndView completeShift(ModelAndView mv,Shift shift) {
		Gson gson = new Gson();
		Map<String, Map<Integer, List<ProblemDetail>>>	cats =
				service.getCategoryMachines(shift.getProblems());

		List<Pushable> pushables = shift.isPushable();
		if(!timer.isTimeSuitable()) {
			pushables.add(Pushable.TIME_DOES_NOT_COME_YET);
		}
		mv.addObject("beginAt",new Time(timer
				.shiftBegin(timer.currentShiftId()
				.getShift()).getTime() + TimeUnit.HOURS.toMillis(8)));
		mv.addObject("theId",shift.getShiftId());
		mv.addObject("catValue",gson.toJson(service.getStandardCategoryNums()));
		mv.addObject("unames", gson.toJson(employeeRepo.findAllUserNames()));
		mv.addObject("problemsValue",gson.toJson(problemRepo.findAllTitles()));
		mv.addObject("whyNot",gson.toJson(pushables));
		if(!cats.isEmpty()) {
			mv.addObject("cats",cats);
		} else {
			mv.addObject("cats",null);
		}
		mv.addObject("flow",shift.getTotalFlowAverage());
		mv.addObject("notes",shift.getNotes());
		mv.addObject("temps",shift.getTemps());
		if(shift.getEmployees() != null) {
			mv.addObject("names",shift.getEmployees().stream()
					.map(e -> new EmployeeName(e)).collect(Collectors.toList()));
		}
		return mv;
	}
}
