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
import com.rhr.heat.dao.NoteRepo;
import com.rhr.heat.dao.ProblemDetailsRepo;
import com.rhr.heat.dao.ProblemRepo;
import com.rhr.heat.dao.TemperatureRepo;
import com.rhr.heat.dao.TotalFlowRepo;
import com.rhr.heat.deep.service.ShiftTimer;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.Problem;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.model.EmployeeName;
import com.rhr.heat.service.Dealer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportPageDataService {
	private final ShiftTimer timer;
	private final TemperatureRepo temperatureRepo;
	private final NoteRepo noteRepo;
    private final MachineRepo machineRepo;
    private final ProblemRepo problemRepo;
    private final ReportComponent component;
    private final EmployeeRepo employeeRepo;
    private final ProblemDetailsRepo problemDetailsRepo;
	private final TotalFlowRepo totalFlowRepo;
	private final Dealer dealer;

	//operations begin
	public Boolean removeAllEmp() {
		if(employeeRepo.removeAllFromShift(component.getCurrentShift().getId())>0){
			return true;
		}
		return false;
	}
	public Boolean removeEmployee(UUID id) {
		if(employeeRepo.removeFromShift(id,component.getCurrentShift().getId())==1){
			return true;
		}
		return false;
	}
	public EmployeeName reportEmployee(String emp) {
		Optional<Employee> employee = employeeRepo.findByUsername(emp);
		if(employee.isPresent()) {
			if(employee.get().isPushable().isEmpty()) {
				if(employeeRepo.saveToShift(employee.get().getId(), component.getCurrentShift().getId())==1){
					return new EmployeeName(employee.get());
				}
			}
		}
		return null;
	}
	public Boolean removeAllNote() {
		if(noteRepo.deleteByShiftId(component.getCurrentShift().getId()) > 0){
			return true;
		}
		return false;
	}
	public Boolean removeNote(String note) {
		if(noteRepo.delete(new Note(component.getCurrentShift().getId(), note))==1){
			return true;
		}
		return false;
	}
	public Note reportNote(String note) {
		Note noteC = new Note(component.getCurrentShift().getId(), note);
		if(noteC.isPushable().isEmpty()) {
			noteRepo.save(noteC);
			return noteC;
		}
		return null;
	}
	public Boolean removeAllTemp() {
		if(temperatureRepo.deleteShiftId
			(component.getCurrentShift().getId())>0){
			return true;
		} else {
			return false;
		}
	}
	public Boolean removeTemp(UUID machineId) {
		if(temperatureRepo.deleteFromShift
			(machineId, component.getCurrentShift().getId())==1){
			return true;
		}
		return false;
	}
	public Temperature reportTemperature(String machine, Integer max, Integer min) {
		Temperature temp = new Temperature(UUID.randomUUID());
		temp.setShiftId(component.getCurrentShift().getId());
		Optional<Machine> machin = component.parseMachine(machine);
		if(machin.isPresent()) {
			temp.setMachine(machin.get());
		}
		temp.setMax(max);
		temp.setMin(min);
		if(temp.isPushable().isEmpty()) {
			temperatureRepo.save(temp);
			return temp;
		} else {
			return null;
		}
	}

	public Boolean removeFlowMachine(UUID flowId,UUID machineId) {
		Optional<Machine> theMachine = machineRepo.findById(machineId);
		if(theMachine.isPresent()) {
			if(machineRepo.allMachinesInFlow(flowId).size() != 1){
				if(machineRepo.removeFromTotalFlow(flowId, theMachine.get().getId())==1){
					return true;
				}
			}
		}
		return false;
	}
	public List<Machine> addFlowMachines(UUID flowId,List<String> machines) {
		List<Optional<Machine>> theMachines = machines.stream()
				.map(m -> component.parseMachine(m)).collect(Collectors.toList());
		List<Machine> result = new ArrayList<>();
		for (Optional<Machine> machine : theMachines) {
			if(machine.isPresent()) {
				if(machineRepo.saveToTotalFlow(flowId, machine.get().getId())==1){
					result.add(machine.get());
				}
			}
		}
		return result;
	}

	public Boolean removeAllFlow() {
		if(totalFlowRepo.deletByShiftId(component.getCurrentShift().getId()) > 0){
			return true;
		}
		return false;
	}

	public Boolean removeFlow() {
		if(totalFlowRepo.deleteFromShift(component.getCurrentShift().getId())==1){
			return true;
		};
		return false;
	}

	public TotalFlow reportTotalFlow(List<String> smachines,
			Integer max,Integer min,String beginTime,String endTime) {
		List<Machine> machines = smachines.stream()
				.map(s -> component.parseMachine(s).orElseThrow())
				.collect(Collectors.toList());
		for (Machine machine : machines) {
			if(machineRepo.findByTheId(machine.getCategory(), machine.getNumber()).isEmpty()) {
				machines.remove(machine);
			}
		}
		TotalFlow tf = new TotalFlow(UUID.randomUUID(),component.getCurrentShift().getId()
			,machines,min, max,GF.getTime(beginTime), GF.getTime(endTime));

		if(tf.isPushable().isEmpty()) {
			totalFlowRepo.save(tf);
			return tf;
		}
		return null;
	}

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
	//operations end

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

	public List<EmployeeName> currentShiftEmployees(){
		return employeeRepo.findByShiftId(component
			.getCurrentShift().getId()).stream()
			.map(e -> new EmployeeName(e)).toList();
	}

	public List<Temperature> currentShiftTemperatures(){
		return temperatureRepo
			.findByShiftId(component.getCurrentShift().getId());
	}

	public List<Note> currentShiftNotes(){
		return noteRepo
			.findByShiftId(component.getCurrentShift().getId());
	}

	public List<TotalFlow> currentShiftFlow(){
		return totalFlowRepo
			.findByShiftId(component.getCurrentShift().getId());
	}
	//fetch data end
}
