package com.rhr.heat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rhr.heat.GF;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.dao.topLayer.ShiftRepo;
import com.rhr.heat.deep.service.DiskIO;
import com.rhr.heat.deep.service.ShiftTimer;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.entity.topLayer.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final ShiftTimer tool;
	private final DiskIO diskIO;
	private final ShiftRepo shiftRepo;
	private final EmployeeRepo employeeRepo;
	private final ProblemRepo problemRepo;
	private final MachineRepo machineRepo;
	
	public void save() {
		Shift s = getCurrentShift();
		if(s.isPushable().isEmpty()) {
			shiftRepo.save(s);
		}
	}
	public ShiftId getTheId() {
		return tool.currentShiftId();
	}
	
	public Shift getCurrentShift() {
		return diskIO.getCurrentShift();
	}
	
	public List<String> problemsTitles(){
		return problemRepo.findAllTitles();
	}
	
	public List<String> usernames(){
		return employeeRepo.findAllUserNames();
	}
	
	public Shift saveShift() {
		Shift oldShift = diskIO.getCurrentShift();
		if(oldShift.isPushable().isEmpty()) {
			shiftRepo.save(oldShift);
		}
		return oldShift;
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
		TotalFlow tf = new TotalFlow(UUID.randomUUID(),null,machines,
				min, max,GF.getTime(beginTime), GF.getTime(endTime));
		if(tf.isPushable().isEmpty()) {
			diskIO.addElement(tf, TotalFlow.class.toString());
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
		Optional<Machine> machin = parseMachine(machine);
		if(machin.isPresent()) {
			temp.setMachine(machin.get());
		} else {
			return "unvalid machine";
		}
		temp.setMax(max);
		temp.setMin(min);
		if(temp.isPushable().isEmpty()) {
			diskIO.addElement(temp, Temperature.class.toString());
			return "temperature record stored succesfully";
		} else {
			return "failed because of "+ temp.isPushable().get(0);
		}
	}
	public void reportNote(String note) {
		Note noteC = new Note(UUID.randomUUID(), note);
		if(noteC.isPushable().isEmpty()) {
			diskIO.addElement(noteC, Note.class.toString());
		}
	}
	
	public String reportEmployee(String emp) {
		Optional<Employee> employee = employeeRepo.findByUsername(emp);
		if(employee.isPresent()) {
			if(employee.get().isPushable().isEmpty()) {
				diskIO.addElement(employee.get(), Employee.class.toString());
				return "successfully added "+ emp;
			} else {
				return "failed because of "+ employee.get().isPushable().get(0);
			}
		} else {
			return "try to define "+emp+"first";
		}
	}
	
	public void removeNote(UUID id) {
		diskIO.removeElement(new Note(id), Note.class.toString());
	}
	
	public String reportProblem(String category,Integer number,
			List<String> problems,String beginTime,String endTime) {
		ProblemDetail pd = new ProblemDetail(UUID.randomUUID());
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
			diskIO.addElement(pd, ProblemDetail.class.toString());
			return pd.getMachine().name()+" problem stored succesfully";
		}
		return "failed to store "+ pd.getMachine().name()
				+" problem beacause of "+ pd.isPushable().get(0);
	}
	
	public String removeMachineProblems(String cat, Integer num) {
		if(diskIO.removeMachineProblems(new Machine(cat,num)))
		{
			return cat +" "+ num+ " all problems deleted sucessfully";
		}
		return "failed";
	}
	
	public String removeProblemProblem(UUID pdId,String title) {
		if(diskIO.removeProblemProblem(new ProblemDetail(pdId), new Problem(title)))
		{
			return "problem "+ title +" deleted sucessfully";
		}
		return "failed";
	}
	
	public String addProblemProblems(UUID pdId,List<String> titles) {
		if(diskIO.addProblemProblems(new ProblemDetail(pdId),
				titles.stream().map(t -> new Problem(t))
				.collect(Collectors.toList())))
		{
			String param = "";
			for (String t : titles) {
				param += t+" ";
			}
			
			return param + " added sucessfully";
		}
		return "failed";
	}
	
	public String removeProblem(UUID id) {
		if(diskIO.removeElement(new ProblemDetail(id), ProblemDetail.class.toString()))
		{
			return "problem deleted sucessfully";
		}
		return "failed";
	}
	
	public String removeEmployee(UUID id) {
		if(diskIO.removeElement(new Employee(id), Employee.class.toString()))
		{
			return "employee deleted sucessfully";
		}
		return "failed";
	}
	
	public void removeAllNote() {
		diskIO.removeAllNote();
	}
	
	public void removeAllFlow() {
		diskIO.removeAllFlow();
	}
	public void removeAllTemp() {
		diskIO.removeAllTemp();
	}
	public void removeAllEmp() {
		diskIO.removeAllEmp();
	}
	
	public String removeTemp(UUID id) {
		if(diskIO.removeFlow(new Temperature(id)))
		{
			return "temperature record removed successfully";
		} 
		return "failed";
	}
	
	public String removeFlow(UUID id) {
		if(diskIO.removeFlow(new TotalFlow(id)))
		{
			return "total flow record removed successfully";
		} 
		return "failed";
	}
	
	public String removeFlowMachine(UUID flowId,String machine) {
		Optional<Machine> theMachine = parseMachine(machine);
		if(theMachine.isPresent()) {
			if(diskIO.removeFlowMachine(new TotalFlow(flowId), theMachine.get()))
			{
				return "total flow record "+ machine +" removed successfully";
			} 
		}
		return "failed";
	}
	
	public String addFlowMachines(UUID flowId,List<String> machines) {
		List<Optional<Machine>> theMachines = machines.stream()
				.map(m -> parseMachine(m)).collect(Collectors.toList());
		int count = 0;
		for (Optional<Machine> machine : theMachines) {
			if(machine.isPresent()) {
				diskIO.addFlowMachine(new TotalFlow(flowId), machine.get());
			} else {
				count++;
			}
		}
		if(count == 0) {
			return "all added succssfully";
		} else if(count == machines.size()) {
			return "all machines need to be defined first";
		} else {
			return "some machines need to be defined first";
		}
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