package com.rhr.heat.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.components.ReportComponent;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.service.ReportService;
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
}