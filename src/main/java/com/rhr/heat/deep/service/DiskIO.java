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
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.Note;
import com.rhr.heat.entity.Problem;
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

	public void removeAllTemp() {
		removeAll(Temperature.class.toString());
	}
	
	public void removeAllNote() {
		removeAll(Note.class.toString());
	}
	
	public void removeAllFlow() {
		removeAll(TotalFlow.class.toString());
	}

	public boolean removeFlow(Temperature temp) {
		return removeElement(temp, Temperature.class.toString());
	}
	
	public Boolean removeFlow(TotalFlow flow) {
		return removeElement(flow, TotalFlow.class.toString());
	}
	
	public Boolean addFlowMachine(TotalFlow flow,Machine machine) {
		flow =fullFillElement(flow, TotalFlow.class.toString());
		TotalFlow newFlow = flow;
		if(newFlow.getSuspendedMachines() == null) {
			newFlow.setSuspendedMachines(new ArrayList<>());
		}
		if(!newFlow.getSuspendedMachines().contains(machine)) {
			newFlow.getSuspendedMachines().add(machine);
		}
		return replaceElement(flow, newFlow, TotalFlow.class.toString());
	}
	
	public Boolean removeFlowMachine(TotalFlow flow,Machine machine) {
		flow =fullFillElement(flow, TotalFlow.class.toString());
		TotalFlow newFlow = flow;
		if(newFlow.getSuspendedMachines() == null) {
			newFlow.setSuspendedMachines(new ArrayList<>());
		}
		newFlow.getSuspendedMachines().remove(machine);
		return replaceElement(flow, newFlow, TotalFlow.class.toString());
	}
	
	public Boolean removeMachineProblems(Machine machine) {
		check();
		List<ProblemDetail> adds = getStoredElements(ProblemDetail.class.toString());
		Boolean result = false;
		List<ProblemDetail> toDelete = new ArrayList<>();
		if(adds != null) {
			for (ProblemDetail problemDetail : adds) {
				if(problemDetail.getMachine().equals(machine)) {
					toDelete.add(problemDetail);
				}
			}
			if(!toDelete.isEmpty()) {
				result = true;
				for (ProblemDetail problemDetail : toDelete) {
					adds.remove(problemDetail);
				}
			}
			writeElements(adds, ProblemDetail.class.toString());
		}
		return result;
	}
	
	public Boolean addProblemProblems(ProblemDetail pd,List<Problem> problems) {
		check();
		List<ProblemDetail> adds = getStoredElements(ProblemDetail.class.toString());
		Boolean result = false;
		if(adds != null) {
			for (ProblemDetail problemDetail : adds) {
				if(problemDetail.equals(pd)) {
					for (Problem p : problems) {
						if(!problemDetail.getProblems().contains(p)) {
							result = problemDetail.getProblems().add(p);
						}
					}
					writeElements(adds, ProblemDetail.class.toString());
					return result;
				}
			}
		}
		return false;
	}
	
	public Boolean removeProblemProblem(ProblemDetail pd,Problem p) {
		check();
		List<ProblemDetail> adds = getStoredElements(ProblemDetail.class.toString());
		Boolean result = false;
		if(adds != null) {
			for (ProblemDetail problemDetail : adds) {
				if(problemDetail.equals(pd)) {
					result = problemDetail.getProblems().remove(p);
				}
			}
			writeElements(adds, ProblemDetail.class.toString());
		}
		return result;
	}
	
	public Boolean removeAll(String cls) {
		File flow =  dataFiles.get(cls);
		if(flow != null) {
			flow.delete();
			try {
				flow.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
	
	
	public <T extends Identity> T fullFillElement(T element,String cls) {
		check();
		List<T> adds = getStoredElements(cls);
		for (T t : adds) {
			if(element.equals(t)) {
				element = t;
			}
		}
		return element;
	}
	public <T extends Identity> Boolean addElement(T element,String cls) {
		check();
		List<T> adds = getStoredElements(cls);
		Boolean addNew = true;
		if(adds == null) {
			adds  = new ArrayList<>();
		} else {
			for (T t : adds) {
				if(t.isSameAs(element)) {
					addNew = false;
				}
			}
		}
		if(addNew) {
			adds.add(element);
			writeElements(adds, cls);
		}
		return addNew;
	}
	
	public <T extends Identity> Boolean replaceElement(T oldT,T newT,String cls) {
		check();
		List<T> adds = getStoredElements(cls);
		Boolean removeOld = false;
		
		if(adds != null) {
			removeOld = adds.remove(oldT);
		}
		
		Boolean addNew = true;
		if(adds == null) {
			adds  = new ArrayList<>();
		} else {
			for (T t : adds) {
				if(t.isSameAs(newT)) {
					addNew = false;
				}
			}
		}
		
		if(addNew) {
			adds.add(newT);
		}
		
		if(removeOld && addNew) {
			writeElements(adds, cls);
			return true;
		} else {
			return false;
		}
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
