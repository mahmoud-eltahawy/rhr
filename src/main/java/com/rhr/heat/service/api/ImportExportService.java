package com.rhr.heat.service.api;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rhr.heat.dao.topLayer.ShiftRepo;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.enums.ShiftOrder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImportExportService {
	private final ShiftRepo shiftRepo;
	private final Map<String, File> dataFiles;

	
	public void exportAfter(Date date) {
		exportThat(shiftRepo.findRecent(date),"After"+date.toString());
	}
	
	public void exportDay(Date date){
		exportThat(shiftRepo.findAll(date),"the-day"+date.toString());
	}
	
	public void exportShift(Date date,String order){
		exportThat(List.of(shiftRepo.findById(date,
				ShiftOrder.valueOf(order)).get()),
				"the-shift"+date.toString()+order);
	}
	
	public void exportBefore(Date date) {
		exportThat(shiftRepo.findOlderThan(date),"Before"+date.toString());
	}
	
	public void exportBetween(Date older, Date newer) {
		exportThat(shiftRepo.findBetween(older,newer),
				"Between"+older.toString()+"And"+newer.toString());
	}
	
	public void exportThat() {
		FileWriter fw;
		try {
			fw = new FileWriter(dataFiles.get("allShifts"));
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
	
	public void exportThat(List<Shift> shifts,String name) {
		FileWriter fw;
		try {
			fw = new FileWriter(new File(dataFiles.get("home").getAbsolutePath()+File.separator+name+".json"));
			new Gson().toJson(shifts.stream().map(s -> {
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
	
	public void importThat() {
		try {
			FileReader fr = new FileReader(dataFiles.get("allShifts"));
			List<Shift> shifts = new Gson().fromJson(fr,
					new TypeToken<List<Shift>>() {}.getType());
			shiftRepo.saveAll(shifts);
			
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void importThat(File file) {
		try {
			FileReader fr = new FileReader(file);
			List<Shift> shifts = new Gson().fromJson(fr,
					new TypeToken<List<Shift>>() {}.getType());
			shiftRepo.saveAll(shifts);
			
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}