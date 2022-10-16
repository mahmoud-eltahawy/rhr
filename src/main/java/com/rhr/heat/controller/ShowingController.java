package com.rhr.heat.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.rhr.heat.enums.Machine.KILEN_ONE;
import static com.rhr.heat.enums.Machine.KILEN_TWO;
import static com.rhr.heat.enums.Machine.KILEN_THREE;
import static com.rhr.heat.enums.Machine.KILEN_FOUR;
import static com.rhr.heat.enums.Machine.KILEN_FIVE;
import static com.rhr.heat.enums.Machine.DRAYER_ONE;
import static com.rhr.heat.enums.Machine.DRAYER_TWO;
import static com.rhr.heat.enums.Machine.DRAYER_THREE;
import static com.rhr.heat.enums.Machine.DRAYER_FOUR;
import static com.rhr.heat.enums.Machine.DRAYER_FIVE;
import static com.rhr.heat.enums.Machine.DRAYER_SIX;
import static com.rhr.heat.enums.Machine.DRAYER_SEVEN;
import static com.rhr.heat.enums.Machine.ATM_ONE;
import static com.rhr.heat.enums.Machine.ATM_TWO;
import static com.rhr.heat.enums.Machine.PROJECT;

import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.enums.Machine;
import com.rhr.heat.service.ShowingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/show")
@RequiredArgsConstructor
public class ShowingController {
	private final ShowingService showingService;

	@GetMapping("/shift")
	public ModelAndView showShift(@RequestParam("id")Long id) {
		ModelAndView mv = new ModelAndView();
		Optional<Shift> s =  showingService.getShift(id);
		if(s.isPresent()) {
			Shift shift =  s.get();
			
			mv.setViewName("showShift");
			
			Map<Machine, List<ProblemDetail>> mp = showingService.getMachinesProblems(shift.getProblems());
			
			
			mv.addObject("theId",shift.getShiftId());
			mv.addObject("k1",mp.get(KILEN_ONE));
			mv.addObject("k2",mp.get(KILEN_TWO));
			mv.addObject("k3",mp.get(KILEN_THREE));
			mv.addObject("k4",mp.get(KILEN_FOUR));
			mv.addObject("k5",mp.get(KILEN_FIVE));
			mv.addObject("d1",mp.get(DRAYER_ONE));
			mv.addObject("d2",mp.get(DRAYER_TWO));
			mv.addObject("d3",mp.get(DRAYER_THREE));
			mv.addObject("d4",mp.get(DRAYER_FOUR));
			mv.addObject("d5",mp.get(DRAYER_FIVE));
			mv.addObject("d6",mp.get(DRAYER_SIX));
			mv.addObject("d7",mp.get(DRAYER_SEVEN));
			mv.addObject("a1",mp.get(ATM_ONE));
			mv.addObject("a2",mp.get(ATM_TWO));
			mv.addObject("p" ,mp.get(PROJECT));
			mv.addObject("flow",shift.getTotalFlowAverage());
			mv.addObject("maxT",shift.getMaxTemperature());
			mv.addObject("minT",shift.getMinTemperature());
			mv.addObject("note",shift.getExceptionalNote());
			mv.addObject("names",shift.getEmployees().stream().map(e ->{
				return e.getFirstName()+" "+e.getMiddleName()+" "+e.getLastName();
			}).collect(Collectors.toList()));
			
			
		} else {
			mv.setViewName("errorPage");
			mv.addObject("message","this shift is not recorded");
		}
		return mv;
	}
}
