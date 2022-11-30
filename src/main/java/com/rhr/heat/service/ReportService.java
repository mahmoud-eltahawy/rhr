package com.rhr.heat.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rhr.heat.components.ReportComponent;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.NoteRepo;
import com.rhr.heat.dao.TemperatureRepo;
import com.rhr.heat.dao.topLayer.ShiftRepo;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.topLayer.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final ReportComponent component;
	private final ShiftRepo shiftRepo;
	private final TemperatureRepo temperatureRepo;
	private final EmployeeRepo employeeRepo;
	private final NoteRepo noteRepo;
	
	public Shift currenShift(){
		return shiftRepo.fullFill(component.getCurrentShift());
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
	
	
	public String removeEmployee(UUID id) {
		employeeRepo.removeFromShift(id, component.getCurrentShift().getId());
		return "employee deleted sucessfully";
	}
	
	public void removeAllNote() {
		noteRepo.deleteByShiftId(component.getCurrentShift().getId());
	}
	public void removeAllEmp() {
		employeeRepo.removeAllFromShift(component.getCurrentShift().getId());
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