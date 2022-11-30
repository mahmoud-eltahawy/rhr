package com.rhr.heat.controller.api;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.service.api.ReportPageDataService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fetch/report")
@RequiredArgsConstructor
public class ReportPageDataController {
  private final ReportPageDataService service;
    
  @GetMapping("/categories/numbers/problems/mapping")
  public Map<String, Map<Integer,
        List<ProblemDetail>>> categoiesNumbersProblems(){
    return	service.categoryNumberProblemMaping();
  }

  @GetMapping("/standard/categories/numbers/mapping")
  public Map<String, List<Integer>> standardCategoriesNumbers(){
    return	service.standardCategoriesNumbers();
  }

  @GetMapping("/all/usernames")
  public List<String> allUsernames(){
    return	service.getAllUserNames();
  }

  @GetMapping("/all/problems/titles")
  public List<String> allProblemsTitles(){
    return	service.getAllProblemsTitles();
  }

  @GetMapping("/current/shift/begin/time")
  public Time shiftBeginTime(){
    return service.getShiftBeginTime();
  }

	@PostMapping("/add/machine/problem")
	public ProblemDetail problem(
			@RequestParam("category")String category,
			@RequestParam("number")Integer number,
			@RequestParam("problems")List<String> problems,
			@RequestParam("beginTime")String beginTime,
			@RequestParam("endTime")String endTime) {
		
		return service.reportProblem(category, number, problems, beginTime, endTime);
	}

	@PostMapping("/remove/machine/problems")
	public Boolean removeMachineProblems(
			@RequestParam("cat")String cat,
			@RequestParam("num")Integer num) {
		return service.removeMachineProblems(cat,num);
	}

	@PostMapping("/remove/problem")
	public Boolean removeProblem(@RequestParam("id")UUID id) {
		return service.removeProblem(id);
	}

	@RequestMapping("/add/problem/problems")
	public List<String> addProblemProblems(
			@RequestParam("id")UUID id,
			@RequestParam("titles")List<String> titles) {
		return service.addProblemProblems(id,titles);
	}

	@RequestMapping("/remove/problem/problem")
	public Boolean removeProblemProblem(
			@RequestParam("id")UUID id,
			@RequestParam("title")String title) {
		return service.removeProblemProblem(id,title);
	}

	@PostMapping("/add/flow")
	public TotalFlow flow(
			@RequestParam("machines")List<String> machines,
			@RequestParam("max")Integer max,
			@RequestParam("min")Integer min,
			@RequestParam("beginTime")String beginTime,
			@RequestParam("endTime")String endTime) {
		return service.reportTotalFlow(machines, max, min, beginTime, endTime);
	}

	@RequestMapping("/remove/flow")
	public Boolean removeFlow() {
		return service.removeFlow();
	}

	@RequestMapping("/remove/all/flow")
	public Boolean removeAllFlow() {
		return service.removeAllFlow();
	}

	@RequestMapping("/add/flow/machines")
	public List<Machine> addFlowMachines(
			@RequestParam("id")UUID flowId,
			@RequestParam("machines")List<String> machines) {
		return service.addFlowMachines(flowId,machines);
	}

	@RequestMapping("/remove/flow/machine")
	public Boolean removeFlowMachine(
			@RequestParam("flow-id")UUID flowId,
			@RequestParam("machine-id")UUID machineId) {
		return service.removeFlowMachine(flowId, machineId);
	}

	@PostMapping("/add/temp")
	public Temperature temperature(
			@RequestParam("max")Integer max ,
			@RequestParam("min")Integer min ,
			@RequestParam("machine")String machine) {
		
		return service.reportTemperature(machine,max,min);
	}

	@PostMapping("/remove/temp")
	public Boolean removeTemp(@RequestParam("id")UUID machineId) {
		return service.removeTemp(machineId);
	}

	@RequestMapping("/remove/all/temp")
	public Boolean removeAllTemp() {
		return service.removeAllTemp();
	}
}