package com.rhr.heat.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.model.Day;
import com.rhr.heat.model.EmpSect;
import com.rhr.heat.model.MachineProfile;
import com.rhr.heat.model.ProblemProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	private final ShiftRepo shiftRepo;
	private final ProblemRepo problemRepo;
	private final EmployeeRepo employeeRepo;
	private final MachineRepo machineRepo;
	
	
	public Optional<Machine> getMachine(UUID id) {
		return machineRepo.findById(id);
	}
	
	public List<Machine> allmachines(){
		return machineRepo.findAll();
	}
	
	public List<String> problemsTitles(){
		return problemRepo.findAllTitles();
	}
	
	public List<String> usernames(){
		return employeeRepo.findAllUserNames();
	}
	
	public Optional<Shift> getShift(UUID id) {
		return shiftRepo.findById(id);
	}
	
	public Optional<Shift> getShift(Date date,ShiftOrder order) {
		return shiftRepo.findById(date, order);
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
		return Day.getDays(shiftRepo.findFromTo(weekNum * 21, 21));
	}
	
	public List<ProblemProfile> pickLastProblems(String problem,Integer problemNum){
		return  problemRepo.findProblemsProfiles(problem, problemNum * 7, 7);
	}
	
	public Problem findProblem(String title) {
		return problemRepo.findByTitle(title).get();
	}
	
	public TreeMap<Date, Day> findDay(Date date) {
		return Day.getDays(shiftRepo.findAll(date));
	}
	
	public List<MachineProfile> pickLastMachineProblems(UUID id,Integer num){
		return  problemRepo.findMachinesProfiles(id, num * 7, 7);
	}
	
	public List<EmpSect> pickLastEmployeeSections(String username,Integer num){
		return EmpSect.days(employeeRepo.findHisLastShifts(username, num * 30, 30));
	}
	
	public Optional<Employee> getEmployee(String username) {
		return employeeRepo.findByUsername(username);
	}
}