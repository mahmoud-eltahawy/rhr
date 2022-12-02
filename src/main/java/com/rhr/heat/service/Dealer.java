package com.rhr.heat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rhr.heat.dao.CategoryRepo;
import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.entity.ProblemDetail;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Dealer {
	private final MachineRepo machineRepo;
	private final CategoryRepo categoryRepo;
	//inputs must be on the same category
	public Map<Integer, List<ProblemDetail>> getMachinesProblems(List<ProblemDetail> allDetails){
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
	
	public Map<String, List<ProblemDetail>> getCategoryProblems(List<ProblemDetail> allDetails){
		Map<String, List<ProblemDetail>> mp = new HashMap<>();
		if(allDetails != null) {
			Gson gson = new Gson();
			for (ProblemDetail pd : allDetails) {
				String cat = gson.toJson(pd.getMachine().getCategory());
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
	
	public Map<String,List<Integer>> getStandardCategoryNums(){
		Map<String,List<Integer>> result = new HashMap<>();
		categoryRepo.findAll().forEach(c -> {
			result.put(new Gson().toJson(c), machineRepo.findCatagoryAllNums(c.getName()));
		});
		return result;
	}
	
	public Map<String, Map<Integer, List<ProblemDetail>>>
		getCategoryMachines(List<ProblemDetail> allDetails){
		
		Map<String, Map<Integer, List<ProblemDetail>>> result = new HashMap<>();
		Map<String, List<ProblemDetail>> cp = getCategoryProblems(allDetails);
		
		for (String cat : cp.keySet()) {
			result.put(new Gson().toJson(cat), getMachinesProblems(cp.get(cat)));
		}
		
		Map<String,List<Integer>> standard = getStandardCategoryNums();

		standard.keySet().forEach(category ->{
			if(result.get(category) == null) {
				Map<Integer, List<ProblemDetail>> v = new HashMap<>(); 
				standard.get(category).forEach(i -> {
					v.put(i, null);
				});
				result.put(category, v);
			} else {
				standard.get(category).forEach(num->{
					if(result.get(category).get(num) ==null) {
						result.get(category).put(num, null);
					};
				});	
			}
		});
		return result;
	}
}
