package com.rhr.heat.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.model.Employee;
import com.rhr.heat.model.Shift;
import com.rhr.heat.model.ShiftId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImportExportService {
	private final EmployeeRepo employeeRepo;
	private final ShiftRepo shiftRepo;

	public void exportAll() {
		FileWriter fw;
		try {
			File home = new File(System.getProperty("user.home")+File.separator+"rhrData");
			home.mkdir();
			
			fw = new FileWriter(new File(home.getAbsolutePath()+File.separator+"empData.json"));
			new Gson().toJson(employeeRepo.findAll().stream().map(e -> {
				e.setId(null);
				return e;
			}).collect(Collectors.toList()),fw);
			fw.close();
			
			home = new File(System.getProperty("user.home")+File.separator+"rhrData"+File.separator+"Data");
			home.mkdir();
			
			fw = new FileWriter(new File(home.getAbsolutePath()+File.separator+"allData.json"));
			new Gson().toJson(shiftRepo.findAll().stream().map(s -> {
				ShiftId si = s.getShiftId();
				si.setId(null);
				s.setShiftId(si);
				s.setEmployees(s.getEmployees().stream().map(e ->{
					e.setId(null);
					return e;
				}).collect(Collectors.toList()));
				s.setProblems(s.getProblems().stream().map(p ->{
					p.setId(null);
					return p;
				}).collect(Collectors.toList()));
				s.setTotalFlowAverage(s.getTotalFlowAverage().stream().map(t ->{
					t.setId(null);
					return t;
				}).collect(Collectors.toList()));
				return s;
			}).collect(Collectors.toList()),fw);
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
			
			employeeRepo.saveAll(emps);
			home = home+File.separator+"Data";
			
			FileReader ar = new FileReader(new File(home+File.separator+"allData.json"));
			List<Shift> shifts = new Gson().fromJson(ar,
					new TypeToken<List<Shift>>() {}.getType());
			shiftRepo.saveAll(shifts);
			
			ar.close();
			er.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}