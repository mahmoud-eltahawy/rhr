package com.rhr.heat.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rhr.heat.deep.service.ShiftTimer;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.enums.Pushable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportControllerDealer {
	private final Dealer service;
	private final ShiftTimer timer;

	public ModelAndView completeShift(ModelAndView mv, Shift shift) {
		Gson gson = new Gson();
		Map<String, Map<Integer, List<ProblemDetail>>> cats = service.getCategoryMachines(shift.getProblems());

		List<Pushable> pushables = shift.isPushable();
		if (!timer.isTimeSuitable()) {
			pushables.add(Pushable.TIME_DOES_NOT_COME_YET);
		}
		mv.addObject("theId", shift.getShiftId());
		mv.addObject("whyNot", gson.toJson(pushables));
		if (!cats.isEmpty()) {
			mv.addObject("cats", cats);
		} else {
			mv.addObject("cats", null);
		}
		return mv;
	}
}
