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

import com.rhr.heat.entity.ProblemDetail;
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
}