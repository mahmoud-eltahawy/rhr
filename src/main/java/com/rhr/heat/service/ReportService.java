package com.rhr.heat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rhr.heat.GF;
import com.rhr.heat.components.ReportComponent;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.dao.NoteRepo;
import com.rhr.heat.dao.ProblemDetailsRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.dao.TemperatureRepo;
import com.rhr.heat.dao.TotalFlowRepo;
import com.rhr.heat.dao.topLayer.ShiftRepo;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.entity.topLayer.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final ReportComponent component;
	private final ShiftRepo shiftRepo;
	private final TotalFlowRepo totalFlowRepo;
	private final TemperatureRepo temperatureRepo;
	private final EmployeeRepo employeeRepo;
	private final ProblemDetailsRepo problemDetailsRepo;
	private final NoteRepo noteRepo;
	private final ProblemRepo problemRepo;
	private final MachineRepo machineRepo;
	
	public Shift currenShift(){
		return shiftRepo.fullFill(component.getCurrentShift());
	}
	
	public String reportTotalFlow(List<String> smachines,
			Integer max,Integer min,String beginTime,String endTime) {
		List<Machine> machines = smachines.stream()
				.map(s -> parseMachine(s).orElseThrow()).collect(Collectors.toList());
		for (Machine machine : machines) {
			if(machineRepo.findByTheId(machine.getCategory(), machine.getNumber()).isEmpty()) {
				return "undefined machine";
			}
		}
		TotalFlow tf = new TotalFlow(UUID.randomUUID(),component.getCurrentShift().getId()
			,machines,min, max,GF.getTime(beginTime), GF.getTime(endTime));
			
		if(tf.isPushable().isEmpty()) {
			totalFlowRepo.save(tf);
			return "total flow record stored successfully";
		}
		return "failed because of "+ tf.isPushable().get(0);
	}
	
	private Optional<Machine> parseMachine(String sm) {
		String[] arr = sm.split("-");
		return machineRepo.findByTheId(arr[0], Integer.parseInt(arr[1]));
	}

	public String reportTemperature(String machine, Integer max, Integer min) {
		Temperature temp = new Temperature(UUID.randomUUID());
		temp.setShiftId(component.getCurrentShift().getId());
		Optional<Machine> machin = parseMachine(machine);
		if(machin.isPresent()) {
			temp.setMachine(machin.get());
		} else {
			return "unvalid machine";
		}
		temp.setMax(max);
		temp.setMin(min);
		if(temp.isPushable().isEmpty()) {
			temperatureRepo.save(temp);
			return "temperature record stored succesfully";
		} else {
			return "failed because of "+ temp.isPushable().get(0);
		}
	}
	public void reportNote(String note) {
		Note noteC = new Note(component.getCurrentShift().getId(), note);
		if(noteC.isPushable().isEmpty()) {
			noteRepo.save(noteC);
		}
	}
	
	public String reportEmployee(String emp) {
		Optional<Employee> employee = employeeRepo.findByUsername(emp);
		if(employee.isPresent()) {
			if(employee.get().isPushable().isEmpty()) {
				employeeRepo.saveToShift(employee.get().getId(), component.getCurrentShift().getId());
				return "successfully added "+ emp;
			} else {
				return "failed because of "+ employee.get().isPushable().get(0);
			}
		} else {
			return "try to define "+emp+"first";
		}
	}
	
	public void removeNote(String note) {
		noteRepo.delete(new Note(component.getCurrentShift().getId(), note));
	}
	
	public String reportProblem(String category,Integer number,
			List<String> problems,String beginTime,String endTime) {
		ProblemDetail pd = new ProblemDetail(UUID.randomUUID());
		pd.setShiftId(component.getCurrentShift().getId());
		Optional<Machine> machine = machineRepo.findByTheId(category,number);
		if(machine.isPresent()) {
			pd.setMachine(machine.get());
		} else {
			return "unvalid machine";
		}
		pd.setBeginTime(GF.getTime(beginTime));
		pd.setEndTime(GF.getTime(endTime));
		List<Problem> pbs = new ArrayList<>();
		for (String p : problems) {
			Optional<Problem> pr = problemRepo.findByTitle(p);
			if(pr.isPresent()) {
				if(pr.get().isPushable().isEmpty()) {
					pbs.add(pr.get());
				} else {
					return "failed because of "+ pr.get().isPushable().get(0);
				}
			} else {
				return "define the problem "+pr.get().getTitle() +" and try again";
			}
		}
		pd.setProblems(pbs);
		if(pd.isPushable().isEmpty()) {
			problemDetailsRepo.save(pd);
			return pd.getMachine().name()+" problem stored succesfully";
		}
		return "failed to store "+ pd.getMachine().name()
				+" problem beacause of "+ pd.isPushable().get(0);
	}
	
	public String removeMachineProblems(String cat, Integer num) {
		Optional<Machine> machine = machineRepo.findByTheId(cat, num);
		if(machine.isPresent()){
			problemDetailsRepo.deleteByMachineId(machine.get().getId());

			return new Machine(cat, num).name() + " all problems deleted sucessfully";
		} else {
			return "machine problems does not exist";
		}
	}
	
	public String removeProblemProblem(UUID pdId,String title) {
		Optional<Problem> problem = problemRepo.findByTitle(title);
		if(problem.isPresent()){
			if(problemRepo.findProblemDetailProblems(pdId).size() != 1){
				problemRepo.deleteFromProblemDetail(title, pdId);
				return "problem deleted sucessfully";
			} else {
				return "can not delete last problem";
			}
		} else {
			return "problem does not exist";
		}
	}
	
	public String addProblemProblems(UUID pdId,List<String> titles) {
		List<Optional<Problem>> problems = titles
			.stream().map(t -> problemRepo.findByTitle(t))
			.collect(Collectors.toList());
		String param = "";
		for (Optional<Problem> p : problems) {
			if(p.isPresent()){
				problemRepo.saveToProblemDetail(p.get().getTitle(), pdId);
				param += p.get().getTitle()+" ";
			}
		}
		if(param.length() == 0){
			return "failed";
		}
		return param + " added sucessfully";
	}
	
	public String removeProblem(UUID id) {
		problemDetailsRepo.deleteById(id);
		return "problem deleted sucessfully";
	}
	
	public String removeEmployee(UUID id) {
		employeeRepo.removeFromShift(id, component.getCurrentShift().getId());
		return "employee deleted sucessfully";
	}
	
	public void removeAllNote() {
		noteRepo.deleteByShiftId(component.getCurrentShift().getId());
	}
	
	public void removeAllFlow() {
		totalFlowRepo.deletByShiftId(component.getCurrentShift().getId());
	}
	public void removeAllTemp() {
		temperatureRepo.deleteShiftId(component.getCurrentShift().getId());
	}
	public void removeAllEmp() {
		employeeRepo.removeAllFromShift(component.getCurrentShift().getId());
	}
	
	public String removeTemp(UUID id) {
		temperatureRepo.deleteFromShift(id, component.getCurrentShift().getId());
		return "temperature record removed successfully";
	}
	
	public String removeFlow(UUID id) {
		totalFlowRepo.deleteFromShift(id,component.getCurrentShift().getId());
		return "total flow record removed successfully";
	}
	
	public String removeFlowMachine(UUID flowId,String machine) {
		Optional<Machine> theMachine = parseMachine(machine);
		if(theMachine.isPresent()) {
			if(machineRepo.allMachinesInFlow(flowId).size() != 1){
				machineRepo.removeFromTotalFlow(flowId, theMachine.get().getId());
				return theMachine.get().name() + " deleted successfully";
			} else {
				return "can not delete last machine";
			}
		}
		return "define machine first";
	}
	
	public String addFlowMachines(UUID flowId,List<String> machines) {
		List<Optional<Machine>> theMachines = machines.stream()
				.map(m -> parseMachine(m)).collect(Collectors.toList());
		String names = "";
		for (Optional<Machine> machine : theMachines) {
			if(machine.isPresent()) {
				machineRepo.saveToTotalFlow(flowId, machine.get().getId());
				names += machine.get().name() +" ";
			}	
		}
		return names +" added successfully";
	}
	
	
	public Shift removeEmployee(Employee employee) {
//		Shift oldShift = tool.getCurrentShift();
//		List<Employee> ems = oldShift.getEmployees();
//		ems = tool.removeFrom(employee, ems);
//		oldShift.setEmployees(ems);
//		tool.writeShift(oldShift);
//		return oldShift;
		return null;
	}
}