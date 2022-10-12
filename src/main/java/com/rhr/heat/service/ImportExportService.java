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
import com.rhr.heat.dao.ShiftRepo;
import com.rhr.heat.model.Shift;
import com.rhr.heat.model.ShiftId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImportExportService {
	private final ShiftRepo shiftRepo;

	public void exportAll() {
		FileWriter fw;
		try {
			File home = new File(System.getProperty("user.home")+File.separator+"rhrData");
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
			FileReader fr = new FileReader(new File(System.getProperty("user.home")
					+File.separator+"rhrData"+File.separator+"allData.json"));
			List<Shift> shifts = new Gson().fromJson(fr,
					new TypeToken<List<Shift>>() {}.getType());
			shiftRepo.saveAll(shifts);
			
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}