package com.rhr.heat.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.enums.Machine;
import com.rhr.heat.model.Day;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowingService {
	private final ShiftRepo shiftRepo;
	
	public Optional<Shift> getShift(Long id) {
		return shiftRepo.findById(id, true);
	}
	
	
	public Map<Machine, List<ProblemDetail>> getMachinesProblems(List<ProblemDetail> allDetails){
		Map<Machine, List<ProblemDetail>> mp = new HashMap<>();
		for (ProblemDetail pd : allDetails) {
			if(mp.get(pd.getMachine()) == null) {
				List<ProblemDetail> pds = new ArrayList<>();
				pds.add(pd);
				mp.put(pd.getMachine(), pds);
			} else {
				List<ProblemDetail> pds = mp.get(pd.getMachine());
				pds.add(pd);
				mp.put(pd.getMachine(), pds);
			}
		}
		
		return mp;
	}
	
	public TreeMap<Date ,Day> pickLastWeeks(Integer weekNum){
		return Day.getDays(shiftRepo.findFromTo(weekNum * 21, 21 ,false));
	}
}