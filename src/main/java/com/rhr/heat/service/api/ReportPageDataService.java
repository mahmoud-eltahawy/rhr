package com.rhr.heat.service.api;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.rhr.heat.components.ReportComponent;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.ProblemDetailsRepo;
import com.rhr.heat.deep.service.ShiftTimer;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.service.Dealer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportPageDataService {
	private final ShiftTimer timer;
    private final ReportComponent component;
    private final EmployeeRepo employeeRepo;
    private final ProblemDetailsRepo problemDetailsRepo;
	private final Dealer dealer;
    
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
}
