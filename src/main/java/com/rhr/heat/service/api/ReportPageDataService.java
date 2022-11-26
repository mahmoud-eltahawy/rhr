package com.rhr.heat.service.api;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    
	//problem operations begin
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
	
	public Boolean removeMachineProblems(String cat, Integer num) {
		Optional<Machine> machine = machineRepo.findByTheId(cat, num);
		if(machine.isPresent()){
			problemDetailsRepo.deleteByMachineId(machine.get().getId());
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean removeProblem(UUID id) {
		if(problemDetailsRepo.deleteById(id) == 1){
			return true;
		}
		return false;
	}
	
	public List<String> addProblemProblems(UUID pdId,List<String> titles) {
		List<Optional<Problem>> problems = titles
			.stream().map(t -> problemRepo.findByTitle(t))
			.collect(Collectors.toList());
		List<String> result = new ArrayList<>();
		for (Optional<Problem> p : problems) {
			if(p.isPresent()){
				if(problemRepo.saveToProblemDetail(p.get().getTitle(), pdId) == 1){
					result.add(p.get().getTitle());
				};
			}
		}
		return result;
	}
	
	public Boolean removeProblemProblem(UUID pdId,String title) {
		Optional<Problem> problem = problemRepo.findByTitle(title);
		if(problem.isPresent()){
			if(problemRepo.findProblemDetailProblems(pdId).size() != 1){
				problemRepo.deleteFromProblemDetail(title, pdId);
				return true;
			}
		}
		return false;
	}
	//problem operations end
    

	//fetch data begin
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
	//fetch data end
}