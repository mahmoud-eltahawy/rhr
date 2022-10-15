package com.rhr.heat.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String showShift(@RequestParam("id")Long id,Model model) {
		Optional<Shift> s =  showingService.getShift(id);
		if(s.isPresent()) {
			Shift shift =  s.get();
			
			
			Map<Machine, List<ProblemDetail>> mp = showingService.getMachinesProblems(shift.getProblems());
			
			model.addAttribute("theId",shift.getShiftId());
			model.addAttribute("k1",mp.get(KILEN_ONE));
			model.addAttribute("k2",mp.get(KILEN_TWO));
			model.addAttribute("k3",mp.get(KILEN_THREE));
			model.addAttribute("k4",mp.get(KILEN_FOUR));
			model.addAttribute("k5",mp.get(KILEN_FIVE));
			model.addAttribute("d1",mp.get(DRAYER_ONE));
			model.addAttribute("d2",mp.get(DRAYER_TWO));
			model.addAttribute("d3",mp.get(DRAYER_THREE));
			model.addAttribute("d4",mp.get(DRAYER_FOUR));
			model.addAttribute("d5",mp.get(DRAYER_FIVE));
			model.addAttribute("d6",mp.get(DRAYER_SIX));
			model.addAttribute("d7",mp.get(DRAYER_SEVEN));
			model.addAttribute("a1",mp.get(ATM_ONE));
			model.addAttribute("a2",mp.get(ATM_TWO));
			model.addAttribute("p" ,mp.get(PROJECT));
			model.addAttribute("flow",shift.getTotalFlowAverage());
			model.addAttribute("maxT",shift.getMaxTemperature());
			model.addAttribute("minT",shift.getMinTemperature());
			model.addAttribute("note",shift.getExceptionalNote());
			model.addAttribute("names",shift.getEmployees().stream().map(e ->{
				return e.getFirstName()+" "+e.getMiddleName()+" "+e.getLastName();
			}).collect(Collectors.toList()));
			
			
			return "showShift";
		} else {
			return "redirect:/customError?message='this shift is not recorded'";
		}
	}
}



