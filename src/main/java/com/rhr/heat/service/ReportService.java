package com.rhr.heat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rhr.heat.Tools;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.TotalFlow;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final Tools tool;
	private final ShiftRepo shiftRepo;
	private final EmployeeRepo employeeRepo;
	private final ProblemRepo problemRepo;
	
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
	
	public Shift stashShift(Shift newShift) {
		Shift oldShift = tool.getCurrentShift();
		oldShift.setProblems(newShift.getProblems());
		oldShift.setTotalFlowAverage(newShift.getTotalFlowAverage());
		oldShift.setEmployees(newShift.getEmployees());
		oldShift.setExceptionalNote(newShift.getExceptionalNote());
		oldShift.setMinTemperature(newShift.getMinTemperature());
		oldShift.setMaxTemperature(newShift.getMaxTemperature());
		
	tool.writeShift(oldShift);
		return oldShift;
	}
	
	public Shift addProblem(ProblemDetail problemDetail) {
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
	
	public Shift setTemperature(Integer max, Integer min) {
		Shift oldShift = tool.getCurrentShift();
		oldShift.setMaxTemperature(max);
		oldShift.setMinTemperature(min);
		tool.writeShift(oldShift);
		return oldShift;
	}
	
	public Shift setNote(String note) {
		Shift oldShift = tool.getCurrentShift();
		oldShift.setExceptionalNote(note);
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