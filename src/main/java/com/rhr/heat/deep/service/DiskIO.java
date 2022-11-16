package com.rhr.heat.deep.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rhr.heat.GF;
import com.rhr.heat.entity.Employee;
import com.rhr.heat.entity.Identity;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.ProblemDetail;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.Temperature;
import com.rhr.heat.entity.TotalFlow;
import com.rhr.heat.entity.topLayer.Shift;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DiskIO {
	private final Map<String, File> dataFiles;
	private final ShiftTimer timer;

	public Shift getCurrentShift() {
		return new Shift(getShiftId(),
				getStoredElements(ProblemDetail.class.toString()),
				getStoredElements(Employee.class.toString()),
				getStoredElements(TotalFlow.class.toString()),
				getStoredElements(Temperature.class.toString()),
				getStoredElements(Note.class.toString()));
	}
	
	public <T extends Identity> void addElement(T element,String cls) {
		check();
		List<T> adds = getStoredElements(cls);
		if(adds == null) {
			adds  = new ArrayList<>();
		}
		adds.add(element);
		writeElements(adds, cls);
	}
	
	public <T extends Identity> Boolean removeElement(T element,String cls) {
		check();
		List<T> adds = getStoredElements(cls);
		Boolean result = false;
		if(adds != null) {
			result = adds.remove(element);
			writeElements(adds, cls);
		}
		return result;
	}
	
	public <T extends Identity> List<T> getStoredElements(String cls){
		List<T> result = null;
		try {
			FileReader fr = new FileReader(dataFiles.get(cls));
			result = new Gson().fromJson(fr, getListType(cls));
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public <T extends Identity> void writeElements(List<T> elements,String cls) {
		try {
			FileWriter fw = new FileWriter(dataFiles.get(cls));
			new Gson().toJson(elements, fw);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void check() {
		if(shiftIdIsOutOfDate(getstoredShiftId())) {
			startNewShift();
		}
	}
	
	public ShiftId getShiftId() {
		ShiftId result = getstoredShiftId();
		if(shiftIdIsOutOfDate(result)) {
			result = updateStoredShiftId();
			startNewShift();
		}
		return result;
	}

	private ShiftId updateStoredShiftId() {
		ShiftId thisShiftId = timer.currentShiftId();
		try {
			FileWriter fw = new FileWriter(dataFiles.get(ShiftId.class.toString()));
			new Gson().toJson(thisShiftId,fw);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return thisShiftId;
	}

	private ShiftId getstoredShiftId() {
		ShiftId result = null;
		try {
			FileReader fr = new FileReader(dataFiles.get(ShiftId.class.toString()));
			result = new Gson().fromJson(fr, ShiftId.class);
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private Boolean shiftIdIsOutOfDate(ShiftId storedId) {
		if(storedId == null )  {
			return true;
		} 
		ShiftId newId = timer.currentShiftId();
		if(GF.equals(storedId.getDate(), newId.getDate()) &&
				storedId.getShift().equals(newId.getShift())) {
			return false;
		}
		return true;
	}
	
	private void startNewShift() {
		try {
			File problems =  dataFiles.get(ProblemDetail.class.toString());
			problems.delete();  problems.createNewFile();
			File employees =  dataFiles.get(Employee.class.toString());
			employees.delete();  employees.createNewFile();
			File flow =  dataFiles.get(TotalFlow.class.toString());
			flow.delete();  flow.createNewFile();
			File temps =  dataFiles.get(Temperature.class.toString());
			temps.delete();  temps.createNewFile();
			File notes =  dataFiles.get(Note.class.toString());
			notes.delete();  notes.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Type getListType(String cls) {
		if(ProblemDetail.class.toString().equals(cls)) {
			return new TypeToken<List<ProblemDetail>>() {}.getType();
		} else if(Employee.class.toString().equals(cls)) {
			return new TypeToken<List<Employee>>() {}.getType();
		} else if(TotalFlow.class.toString().equals(cls)) {
			return new TypeToken<List<TotalFlow>>() {}.getType();
		} else if(Temperature.class.toString().equals(cls)) {
			return new TypeToken<List<Temperature>>() {}.getType();
		} else if(Note.class.toString().equals(cls)) {
			return new TypeToken<List<Note>>() {}.getType();
		}
		return null;
	}
}
