package com.rhr.heat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rhr.heat.Tools;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.dao.topLayer.ShiftRepo;
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
	private final Tools tool;
	private final ShiftRepo shiftRepo;
	private final EmployeeRepo employeeRepo;
	private final ProblemRepo problemRepo;
	private final MachineRepo machineRepo;
	
	public void save() {
		Shift s = getCurrentShift();
		s.setEmployees(s.getEmployees().stream()
				.map(e -> employeeRepo.findByUsername(e.getUsername()).orElseThrow())
				.collect(Collectors.toList()));
		if(s.isPushable()) {
			shiftRepo.save(s);
		}
	}
	public ShiftId getTheId() {
		return tool.thisShift();
	}
	
	public Shift getCurrentShift() {
		return tool.getCurrentShift();
	}
	
	public List<String> problemsTitles(){
		return problemRepo.findAllTitles();
	}
	
	public List<String> usernames(){
		return employeeRepo.findAllUserNames();
	}
	
	public Shift saveShift() {
		Shift oldShift = tool.getCurrentShift();
		if(oldShift.isPushable()) {
			shiftRepo.save(oldShift);
		}
		return oldShift;
	}
	
	public String reportProblem(String category,Integer number,
			List<String> problems,String beginTime,String endTime) {
		ProblemDetail pd = new ProblemDetail();
		pd.setId(UUID.randomUUID());
		Optional<Machine> machine = machineRepo.findByTheId(category,number);
		if(machine.isPresent()) {
			if(machine.get().isPushable()) {
				System.out.println(machine.get().getCategory() +" pass");
				pd.setMachine(machine.get());
			}
		}
		pd.setBeginTime(Tools.getTime(beginTime));
		pd.setEndTime(Tools.getTime(endTime));
		List<Problem> pbs = new ArrayList<>();
		for (String p : problems) {
			Optional<Problem> pr = problemRepo.findByTitle(p);
			if(pr.isPresent()) {
				if(pr.get().isPushable()) {
					System.out.println(pr.get().getTitle()+" pass");
					pbs.add(pr.get());
				}
			}
		}
		pd.setProblems(pbs);
		if(pd.isPushable()) {
			System.out.println("final pass");
			addProblem(pd);
			return "problems stored succesfully";
		} else {
			return "problem is unvalid";
		}
	}
	
	private Shift addProblem(ProblemDetail problemDetail) {
		Shift oldShift = tool.getCurrentShift();
		oldShift.setProblems(tool.addTo(problemDetail, oldShift.getProblems()));
		tool.writeShift(oldShift);
		return oldShift;
	}
	
	public Shift addTotalFlow(TotalFlow totalFlow) {
		Shift oldShift = tool.getCurrentShift();
		oldShift.setTotalFlowAverage(tool.addTo(totalFlow, oldShift.getTotalFlowAverage()));
		tool.writeShift(oldShift);
		return oldShift;
	}
	
	public Shift addEmployee(String emp) {
		Optional<Employee> employee = employeeRepo.findByUsername(emp);
		Shift oldShift = tool.getCurrentShift();
		if(employee.isPresent()) {
			oldShift.setEmployees(tool.addTo(employee.get(), oldShift.getEmployees()));
			tool.writeShift(oldShift);
		}
		return oldShift;
	}
	
	
	public Shift addTemp(Temperature temp) {
		Shift oldShift = tool.getCurrentShift();
		oldShift.setTemps(tool.addTo(temp, oldShift.getTemps()));
		tool.writeShift(oldShift);
		return oldShift;
	}
	
	public Shift addNote(Note note) {
		Shift oldShift = tool.getCurrentShift();
		oldShift.setNotes(tool.addTo(note, oldShift.getNotes()));
		tool.writeShift(oldShift);
		return oldShift;
	}
	
	public Shift removeTemp(Temperature temp) {
		Shift oldShift = tool.getCurrentShift();
		List<Temperature> tms = oldShift.getTemps();
		tms = tool.removeFrom(temp, tms);
		oldShift.setTemps(tms);
		tool.writeShift(oldShift);
		return oldShift;
	}
	
	public Shift removeNote(Note note) {
		Shift oldShift = tool.getCurrentShift();
		List<Note> nts = oldShift.getNotes();
		nts = tool.removeFrom(note, nts);
		oldShift.setNotes(nts);
		tool.writeShift(oldShift);
		return oldShift;
	}

	
	public Shift removeTotalFlow(TotalFlow totalFlow) {
		Shift oldShift = tool.getCurrentShift();
		List<TotalFlow> tfs = oldShift.getTotalFlowAverage();
		tfs = tool.removeFrom(totalFlow, tfs);
		oldShift.setTotalFlowAverage(tfs);
		tool.writeShift(oldShift);
		return oldShift;
	}
	
	public Shift removeEmployee(Employee employee) {
		Shift oldShift = tool.getCurrentShift();
		List<Employee> ems = oldShift.getEmployees();
		ems = tool.removeFrom(employee, ems);
		oldShift.setEmployees(ems);
		tool.writeShift(oldShift);
		return oldShift;
	}
	
	public Shift removeProblem(ProblemDetail problemDetail) {
		Shift oldShift = tool.getCurrentShift();
		List<ProblemDetail> pds = oldShift.getProblems();
		pds = tool.removeFrom(problemDetail, pds);
		oldShift.setProblems(pds);
		tool.writeShift(oldShift);
		return oldShift;
	}
}