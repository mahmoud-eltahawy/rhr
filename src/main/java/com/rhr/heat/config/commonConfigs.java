package com.rhr.heat.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.entity.TotalFlow;

@Configuration
public class commonConfigs {

	@Bean
	public Map<String,File> dataFiles() {
		File home = new File(System.getProperty("user.home")+File.separator+"rhrData");
		home.mkdir();
		
		File allShifts = new File(home.getAbsolutePath()+File.separator+"allShifts.json");
		File shiftId = new File(home.getAbsolutePath()+File.separator+"shiftId.json");
		File problems = new File(home.getAbsolutePath()+File.separator+"problems.json");
		File employees = new File(home.getAbsolutePath()+File.separator+"employees.json");
		File totalFlow = new File(home.getAbsolutePath()+File.separator+"TotalFlow.json");
		File temps = new File(home.getAbsolutePath()+File.separator+"temps.json");
		File notes = new File(home.getAbsolutePath()+File.separator+"notes.json");
		
		try {
			allShifts.createNewFile();
			shiftId.createNewFile();
			problems.createNewFile();
			employees.createNewFile();
			totalFlow.createNewFile();
			temps.createNewFile();
			notes.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String,File> map = new HashMap<>();
		map.put("home", home);
		map.put("allShifts", allShifts);
		map.put(ShiftId.class.toString(), shiftId);
		map.put(ProblemDetail.class.toString(), problems);
		map.put(Employee.class.toString(), employees);
		map.put(TotalFlow.class.toString(), totalFlow);
		map.put(Temperature.class.toString(), temps);
		map.put(Note.class.toString(), notes);
		
		return map;
	}
}
