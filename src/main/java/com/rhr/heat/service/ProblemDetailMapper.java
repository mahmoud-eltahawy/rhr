package com.rhr.heat.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rhr.heat.dao.CategoryRepo;
import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.entity.Category;
import com.rhr.heat.entity.ProblemDetail;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProblemDetailMapper {
	private final MachineRepo machineRepo;
	private final CategoryRepo categoryRepo;
	
	public Map<Category,List<Integer>> getStandardCategoryNums(){
		Map<Category,List<Integer>> result = new HashMap<>();
		categoryRepo.findAll().forEach(c -> {
			result.put(c, machineRepo.findCatagoryAllNums(c.getName()));
		});
		return result;
	}
	
	public List<ProblemDetail> getStandardCategoryNumbers(){
		List<ProblemDetail> result = new ArrayList<>();
		categoryRepo.findAllNames().forEach(category -> {
			machineRepo.findCatagoryAllNums(category)
				.forEach(number -> {
					result.add(new ProblemDetail(category, number));
				});
		});
		return result;
	}

	public Collection<Collection<List<ProblemDetail>>> getProblems(List<ProblemDetail> allDetails){
		allDetails.addAll(getStandardCategoryNumbers());
		return grouper(allDetails);
	}


	public Collection<Collection<List<ProblemDetail>>> grouper(List<ProblemDetail> pds){
		return pds.stream()
		.collect(Collectors.groupingBy(p -> p.getMachine().getCategory().getName(),
		Collectors.groupingBy(p -> p.getMachine().getNumber())))
			.values().stream().map(m -> m.values()).toList();
	}
}
