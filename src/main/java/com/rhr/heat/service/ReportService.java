package com.rhr.heat.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rhr.heat.Tools;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Shift;
import com.rhr.heat.entity.TotalFlow;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final Tools tool;
	
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
		List<ProblemDetail> pds = oldShift.getProblems();
		if(pds == null) {
			pds =new ArrayList<>();
			pds.add(problemDetail);
			oldShift.setProblems(pds);
		} else {
			if(!tool.exists(problemDetail,pds)) {
				oldShift.getProblems().add(problemDetail);
			}
		}
		
		tool.writeShift(oldShift);
		return oldShift;
	}
	
	public Shift addTotalFlow(TotalFlow totalFlow) {
		Shift oldShift = tool.getCurrentShift();
		List<TotalFlow> tfs = oldShift.getTotalFlowAverage();
		if(tfs == null) {
			tfs =new ArrayList<>();
			tfs.add(totalFlow);
			oldShift.setTotalFlowAverage(tfs);
		} else {
			if(!tool.exists(totalFlow,tfs)) {
				oldShift.getTotalFlowAverage().add(totalFlow);
			}
		}
		
		tool.writeShift(oldShift);
		return oldShift;
	}
	
	public Shift addEmployee(Employee employee) {
		Shift oldShift = tool.getCurrentShift();
		List<Employee> ems = oldShift.getEmployees();
		if(ems == null) {
			ems =new ArrayList<>();
			ems.add(employee);
			oldShift.setEmployees(ems);
		} else {
			if(!tool.exists(employee, ems)) {
				oldShift.getEmployees().add(employee);
			}
		}
		
		tool.writeShift(oldShift);
		return oldShift;
	}

	public Shift removeTotalFlow(TotalFlow totalFlow) {
		Shift oldShift = tool.getCurrentShift();
		List<TotalFlow> tfs = oldShift.getTotalFlowAverage();
		if(tfs != null) {
			Iterator<TotalFlow> it = tfs.iterator();
			while(it.hasNext()) {
				TotalFlow c = it.next();
				if(Tools.timeEquals(c.getCaseBeginTime(), totalFlow.getCaseBeginTime()) &
						Tools.timeEquals(c.getCaseEndTime(), totalFlow.getCaseEndTime())) {
					it.remove();
				}
			}
		}	
		
		oldShift.setTotalFlowAverage(tfs);
		tool.writeShift(oldShift);
		return oldShift;
	}
	
	public Shift removeEmployee(Employee employee) {
		Shift oldShift = tool.getCurrentShift();
		List<Employee> ems = oldShift.getEmployees();
		if(ems != null) {
			Iterator<Employee> it = ems.iterator();
			while(it.hasNext()) {
				if(it.next().getUsername().equals(employee.getUsername())) {
					it.remove();
				}
			}
		}	
		oldShift.setEmployees(ems);
		tool.writeShift(oldShift);
		return oldShift;
	}
}