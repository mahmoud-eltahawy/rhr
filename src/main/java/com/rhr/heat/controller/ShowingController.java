package com.rhr.heat.controller;

import java.util.ArrayList;
import java.util.List;
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

import com.rhr.heat.enums.Machine;
import com.rhr.heat.model.ProblemDetail;
import com.rhr.heat.model.Shift;
import com.rhr.heat.service.ShowingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/show")
@RequiredArgsConstructor
public class ShowingController {
	private final ShowingService showingService;

	@GetMapping("/shift")
	public String showShift(@RequestParam("id")Long id,Model model) {
		Shift shift =  showingService.getShift(id).get();
		List<ProblemDetail> kilens = new ArrayList<>(); 
		List<ProblemDetail> drayers = new ArrayList<>(); 
		List<ProblemDetail> atms = new ArrayList<>(); 
		List<ProblemDetail> project = new ArrayList<>(); 
		for (ProblemDetail pd : shift.getProblems()) {
			if(pd.getMachine() == KILEN_ONE |
					pd.getMachine() == KILEN_TWO |
					pd.getMachine() == KILEN_THREE |
					pd.getMachine() == KILEN_FOUR |
					pd.getMachine() == KILEN_FIVE) {
				kilens.add(pd);
			} else if(pd.getMachine() ==DRAYER_ONE |
					pd.getMachine() == DRAYER_TWO |
					pd.getMachine() == DRAYER_THREE |
					pd.getMachine() == DRAYER_FOUR |
					pd.getMachine() == DRAYER_FIVE |
					pd.getMachine() == DRAYER_SIX |
					pd.getMachine() == DRAYER_SEVEN) {
				drayers.add(pd);
			} else if(pd.getMachine() == ATM_ONE | pd.getMachine() == ATM_TWO ) {
				atms.add(pd);
			} else if(pd.getMachine() == PROJECT) {
				project.add(pd);
			}
		}
		
		
		model.addAttribute("theId",shift.getShiftId());
		model.addAttribute("kilens",kilens);
		model.addAttribute("drayers",drayers);
		model.addAttribute("atms",atms);
		model.addAttribute("project",project);
		model.addAttribute("flow",shift.getTotalFlowAverage());
		model.addAttribute("maxT",shift.getMaxTemperature());
		model.addAttribute("minT",shift.getMinTemperature());
		model.addAttribute("names",shift.getEmployees().stream().map(e ->{
			return e.getFirstName()+" "+e.getMiddleName()+" "+e.getLastName();
		}).collect(Collectors.toList()));
		
		
		return "showShift";
	}
}







