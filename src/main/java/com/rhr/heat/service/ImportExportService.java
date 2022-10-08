package com.rhr.heat.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.ProblemDetailsRepo;
import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.dao.TotalFlowRepo;
import com.rhr.heat.model.Employee;
import com.rhr.heat.model.Shift;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImportExportService {
	private final EmployeeRepo employeeRepo;
	private final ProblemDetailsRepo problemDetailsRepo;
	private final TotalFlowRepo totalFlowRepo;
	private final ShiftRepo shiftRepo;

	public void exportAll() {
		try {
			File home = new File(System.getProperty("user.home")+File.separator+"rhrData");
			home.mkdir();
			FileWriter fw = new FileWriter(new File(home.getAbsolutePath()+File.separator+"allData.json"));
			new Gson().toJson(shiftRepo.findAll(),fw);
			fw.close();
			fw = new FileWriter(new File(home.getAbsolutePath()+File.separator+"empData.json"));
			new Gson().toJson(employeeRepo.findAll(),fw);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void importAll() {
		try {
			String home = System.getProperty("user.home")+File.separator+"rhrData";
			
			FileReader er = new FileReader(new File(home+File.separator+"empData.json"));
			List<Employee> emps = new Gson().fromJson(er,
					new TypeToken<List<Employee>>() {}.getType());
			emps.forEach(e -> {
				e.setId(null);
			});
			
			employeeRepo.saveAll(emps);
			
			FileReader ar = new FileReader(new File(home+File.separator+"allData.json"));
			List<Shift> shifts = new Gson().fromJson(ar,
					new TypeToken<List<Shift>>() {}.getType());
			shifts.forEach(s -> {
				s.getProblems().forEach(p -> {
					p.setId(null);
				});
				problemDetailsRepo.saveAll(s.getProblems());
				s.getTotalFlowAverage().forEach(t -> {
					t.setId(null);
				});
				totalFlowRepo.saveAll(s.getTotalFlowAverage());
			});
			shiftRepo.saveAll(shifts);
			
			ar.close();
			er.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}