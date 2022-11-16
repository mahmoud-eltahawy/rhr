package com.rhr.heat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import com.rhr.heat.enums.Pushable;

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
	
	public String reportProblem(String category,Integer number,
			List<String> problems,String beginTime,String endTime) {
		ProblemDetail pd = new ProblemDetail(UUID.randomUUID());
		Optional<Machine> machine = machineRepo.findByTheId(category,number);
		if(machine.isPresent()) {
			if(machine.get().isPushable().isEmpty()) {
				pd.setMachine(machine.get());
			} else {
				pd.isPushable().addAll(machine.get().isPushable());
			}
		} else {
			pd.isPushable().add(Pushable.SPECIFIED_MACHINE_DOES_NOT_EXIST);
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
					pd.isPushable().addAll(pr.get().isPushable());
				}
			} else {
				pd.isPushable().add(Pushable.SPECIFIED_PROBLEM_DOES_NOT_EXIST);
			}
		}
		pd.setProblems(pbs);
		if(pd.isPushable().isEmpty()) {
			diskIO.addElement(pd, ProblemDetail.class.toString());
			return pd.getMachine().name()+" problem stored succesfully";
		} else {
			return "failed to store "+ pd.getMachine().name()
					+" problem beacause of "+ pd.isPushable().get(0);
		}
	}
	
	public String removeMachineProblems(String cat, Integer num) {
		if(diskIO.removeMachineProblems(new Machine(cat,num)))
		{
			return "deleted sucessfully";
		}
		return "failed";
	}
	
	public String removeProblemProblem(UUID pdId,String title) {
		if(diskIO.removeProblemProblem(new ProblemDetail(pdId), new Problem(title)))
		{
			return "deleted sucessfully";
		}
		return "failed";
	}
	
	public String removeProblem(UUID id) {
		if(diskIO.removeElement(new ProblemDetail(id), ProblemDetail.class.toString()))
		{
			return "deleted sucessfully";
		}
		return "failed";
	}
	
	public Shift addTotalFlow(TotalFlow totalFlow) {
//		Shift oldShift = tool.getCurrentShift();
//		oldShift.setTotalFlowAverage(tool.addTo(totalFlow, oldShift.getTotalFlowAverage()));
//		tool.writeShift(oldShift);
		return null;
	}
	
	public Shift addEmployee(String emp) {
//		Optional<Employee> employee = employeeRepo.findByUsername(emp);
//		Shift oldShift = tool.getCurrentShift();
//		if(employee.isPresent()) {
//			oldShift.setEmployees(tool.addTo(employee.get(), oldShift.getEmployees()));
//			tool.writeShift(oldShift);
//		}
//		return oldShift;
		return null;
	}
	
	
	public Shift addTemp(Temperature temp) {
//		Shift oldShift = tool.getCurrentShift();
//		oldShift.setTemps(tool.addTo(temp, oldShift.getTemps()));
//		tool.writeShift(oldShift);
//		return oldShift;
		return null;
	}
	
	public Shift addNote(Note note) {
//		Shift oldShift = tool.getCurrentShift();
//		oldShift.setNotes(tool.addTo(note, oldShift.getNotes()));
//		tool.writeShift(oldShift);
//		return oldShift;
		return null;
	}
	
	public Shift removeTemp(Temperature temp) {
//		Shift oldShift = tool.getCurrentShift();
//		List<Temperature> tms = oldShift.getTemps();
//		tms = tool.removeFrom(temp, tms);
//		oldShift.setTemps(tms);
//		tool.writeShift(oldShift);
//		return oldShift;
		return null;
	}
	
	public Shift removeNote(Note note) {
//		Shift oldShift = tool.getCurrentShift();
//		List<Note> nts = oldShift.getNotes();
//		nts = tool.removeFrom(note, nts);
//		oldShift.setNotes(nts);
//		tool.writeShift(oldShift);
//		return oldShift;
		return null;
	}

	
	public Shift removeTotalFlow(TotalFlow totalFlow) {
//		Shift oldShift = tool.getCurrentShift();
//		List<TotalFlow> tfs = oldShift.getTotalFlowAverage();
//		tfs = tool.removeFrom(totalFlow, tfs);
//		oldShift.setTotalFlowAverage(tfs);
//		tool.writeShift(oldShift);
//		return oldShift;
		return null;
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