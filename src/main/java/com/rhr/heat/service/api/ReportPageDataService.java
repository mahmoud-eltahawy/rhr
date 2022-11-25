package com.rhr.heat.service.api;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.rhr.heat.GF;
import com.rhr.heat.components.ReportComponent;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.dao.ProblemDetailsRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.deep.service.ShiftTimer;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.service.Dealer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportPageDataService {
	private final ShiftTimer timer;
    private final MachineRepo machineRepo;
    private final ProblemRepo problemRepo;
    private final ReportComponent component;
    private final EmployeeRepo employeeRepo;
    private final ProblemDetailsRepo problemDetailsRepo;
	private final Dealer dealer;
    
	public ProblemDetail reportProblem(String category,Integer number,
			List<String> problems,String beginTime,String endTime) {
		ProblemDetail pd = new ProblemDetail(UUID.randomUUID());
		pd.setShiftId(component.getCurrentShift().getId());
		Optional<Machine> machine = machineRepo.findByTheId(category,number);
		if(machine.isPresent()) {
			pd.setMachine(machine.get());
		}
		pd.setBeginTime(GF.getTime(beginTime));
		pd.setEndTime(GF.getTime(endTime));
		List<Problem> pbs = new ArrayList<>();
		for (String p : problems) {
			Optional<Problem> pr = problemRepo.findByTitle(p);
			if(pr.isPresent()) {
				if(pr.get().isPushable().isEmpty()) {
					pbs.add(pr.get());
				}
			}
		}
		pd.setProblems(pbs);
		if(pd.isPushable().isEmpty()) {
			problemDetailsRepo.save(pd);
			return pd;
		}
		return null;
	}
    
    public Map<String, Map<Integer, List<ProblemDetail>>> categoryNumberProblemMaping(){
        return dealer.getCategoryMachines(problemDetailsRepo
            .findByShiftId(component.getCurrentShift().getId()));
    }
    
    public Map<String, List<Integer>> standardCategoriesNumbers(){
        return dealer.getStandardCategoryNums();
    }

    public List<String> getAllUserNames(){
        return employeeRepo.findAllUserNames();
    }

    public Time getShiftBeginTime(){
		return new Time(timer
				.shiftBegin(timer.currentShiftId()
				.getShift()).getTime() + TimeUnit.HOURS.toMillis(8));
    }

    public List<String> getAllProblemsTitles(){
		return problemRepo.findAllTitles();
    }
}