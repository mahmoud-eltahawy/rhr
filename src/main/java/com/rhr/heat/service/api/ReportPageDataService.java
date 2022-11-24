package com.rhr.heat.service.api;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rhr.heat.components.ReportComponent;
import com.rhr.heat.dao.ProblemDetailsRepo;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.service.Dealer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportPageDataService {
    private final ReportComponent component;
    private final ProblemDetailsRepo problemDetailsRepo;
	private final Dealer dealer;
    
    public Map<String, Map<Integer, List<ProblemDetail>>> categoryNumberProblemMaping(){
        return dealer.getCategoryMachines(problemDetailsRepo
            .findByShiftId(component.getCurrentShift().getId()));
    }
    
    public Map<String, List<Integer>> standardCategoriesNumbers(){
        return dealer.getStandardCategoryNums();
    }
}
