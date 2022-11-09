package com.rhr.heat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.topLayer.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonService {
	private final MachineRepo machineRepo;
	
	public ModelAndView completeShift(ModelAndView mv,Shift shift) {
		Map<String, Map<Integer, List<ProblemDetail>>>	cats =
				getCategoryMachines(shift.getProblems());
		mv.addObject("theId",shift.getShiftId());
		if(!cats.isEmpty()) {
			mv.addObject("cats",cats);
		} else {
			mv.addObject("cats",null);
		}
		if(shift.getTotalFlowAverage() != null) {
			mv.addObject("flow",shift.getTotalFlowAverage());
		} else {
			mv.addObject("flow",new ArrayList<>());
		}
		if(shift.getTotalFlowAverage() != null) {
			mv.addObject("temps",shift.getTemps());
		} else {
			mv.addObject("temps",new ArrayList<>());
		}
		if(shift.getTotalFlowAverage() != null) {
			mv.addObject("notes",shift.getNotes());
		} else {
			mv.addObject("notes",new ArrayList<>());
		}
		if(shift.getEmployees() != null) {
			mv.addObject("names",shift.getEmployees().stream().map(e ->{
				return e.getFirstName()+" "+e.getMiddleName()+" "+e.getLastName();
			}).collect(Collectors.toList()));
		} else {
			mv.addObject("names",new ArrayList<>());
		}
		return mv;
	}
	
	//inputs must be on the same category
	private Map<Integer, List<ProblemDetail>> getMachinesProblems(List<ProblemDetail> allDetails){
		Map<Integer, List<ProblemDetail>> mp = new HashMap<>();
		if(allDetails != null) {
			for (ProblemDetail pd : allDetails) {
				Integer num = pd.getMachine().getNumber();
				if(mp.get(num) == null) {
					List<ProblemDetail> pds = new ArrayList<>();
					pds.add(pd);
					mp.put(num, pds);
				} else {
					List<ProblemDetail> pds = mp.get(num);
					pds.add(pd);
					mp.put(num, pds);
				}
			}
		}
		return mp;
	}
	
	private Map<String, List<ProblemDetail>> getCategoryProblems(List<ProblemDetail> allDetails){
		Map<String, List<ProblemDetail>> mp = new HashMap<>();
		if(allDetails != null) {
			for (ProblemDetail pd : allDetails) {
				String cat = pd.getMachine().getCategory();
				if(mp.get(cat) == null) {
					List<ProblemDetail> pds = new ArrayList<>();
					pds.add(pd);
					mp.put(cat, pds);
				} else {
					List<ProblemDetail> pds = mp.get(cat);
					pds.add(pd);
					mp.put(cat, pds);
				}
			}
		}
		return mp;
	}
	
	private Map<String,List<Integer>> getStandardCategoryNums(){
		Map<String,List<Integer>> result = new HashMap<>();
		
		machineRepo.findAllCatagories().forEach(c -> {
			result.put(c, machineRepo.findCatagoryAllNums(c));
		});
		return result;
	}
	
	public Map<String, Map<Integer, List<ProblemDetail>>>
		getCategoryMachines(List<ProblemDetail> allDetails){
		
		Map<String, Map<Integer, List<ProblemDetail>>> result = new HashMap<>();
		Map<String, List<ProblemDetail>> cp = getCategoryProblems(allDetails);
		
		for (String cat : cp.keySet()) {
			result.put(cat, getMachinesProblems(cp.get(cat)));
		}
		
		Map<String,List<Integer>> standard = getStandardCategoryNums();
		if(!result.isEmpty()) {
			standard.keySet().forEach(category ->{
				if(result.get(category) == null) {
					result.put(category, null);
				} else {
					standard.get(category).forEach(num->{
						if(result.get(category).get(num) ==null) {
							result.get(category).put(num, null);
						};
					});	
				}
			});
		}
		return result;
	}
}
