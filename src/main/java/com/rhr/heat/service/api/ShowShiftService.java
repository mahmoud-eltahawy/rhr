package com.rhr.heat.service.api;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.NoteRepo;
import com.rhr.heat.dao.ProblemDetailsRepo;
import com.rhr.heat.dao.ShiftIdRepo;
import com.rhr.heat.dao.TemperatureRepo;
import com.rhr.heat.dao.TotalFlowRepo;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.model.EmployeeName;
import com.rhr.heat.service.Dealer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowShiftService {
	private final ShiftIdRepo shiftIdRepo;
	private final TemperatureRepo temperatureRepo;
	private final NoteRepo noteRepo;
    private final EmployeeRepo employeeRepo;
    private final ProblemDetailsRepo problemDetailsRepo;
	private final TotalFlowRepo totalFlowRepo;
	private final Dealer dealer;

    public Map<String, String> categoryNumberProblemMaping(UUID id){
        return dealer.getStringifiedCategoryMachines(problemDetailsRepo
            .findByShiftId(id));
    }

	public List<EmployeeName> currentShiftEmployees(UUID id){
		return employeeRepo.findByShiftId(id).stream()
			.map(e -> new EmployeeName(e)).toList();
	}

	public List<Temperature> currentShiftTemperatures(UUID id){
		return temperatureRepo.findByShiftId(id);
	}

	public List<Note> currentShiftNotes(UUID id){
		return noteRepo.findByShiftId(id);
	}

	public List<TotalFlow> currentShiftFlow(UUID id){
		return totalFlowRepo.findByShiftId(id);
	}

	public ShiftId currentShift(UUID id){
		return shiftIdRepo.findById(id).orElseThrow();
	}
}
