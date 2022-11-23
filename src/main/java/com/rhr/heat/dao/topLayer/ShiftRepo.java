package com.rhr.heat.dao.topLayer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.NoteRepo;
import com.rhr.heat.dao.ProblemDetailsRepo;
import com.rhr.heat.dao.ShiftIdRepo;
import com.rhr.heat.dao.TemperatureRepo;
import com.rhr.heat.dao.TotalFlowRepo;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.enums.Pushable;
import com.rhr.heat.enums.ShiftOrder;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShiftRepo {
	private final EmployeeRepo employeeRepo;
	private final ProblemDetailsRepo problemDetailsRepo;
	private final ShiftIdRepo shiftIdRepo;
	private final TotalFlowRepo totalFlowRepo;
	private final TemperatureRepo temperatureRepo;
	private final NoteRepo noteRepo;

	public List<Shift> findAll() {
		 return shiftIdRepo.findAll().stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}
	
	public List<Shift> findAll(Date date) {
		 return shiftIdRepo.findAll(date).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<Shift> findAll(ShiftOrder order) {
		 return shiftIdRepo.findAll(order).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public Optional<Shift> findById(UUID uuid) {
		 Optional<ShiftId> shiftId = shiftIdRepo.findById(uuid);
		 if(shiftId.isPresent()) {
			return Optional.of(fullFill(shiftId.get())); 
		 } else {
			return Optional.of(null); 
		 }
	}

	public Optional<Shift> findById(Date date, ShiftOrder order) {
		 Optional<ShiftId> shiftId = shiftIdRepo.findById(date,order);
		 if(shiftId.isPresent()) {
			return Optional.of(fullFill(shiftId.get())); 
		 } else {
			return Optional.ofNullable(null); 
		 }
	}

	public List<Shift> findOlderThan(Date date) {
		 return shiftIdRepo.findOlderThan(date).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<Shift> findOlderThan(Date date,ShiftOrder order) {
		 return shiftIdRepo.findOlderThan(date,order).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<Shift> findRecent(Date date) {
		 return shiftIdRepo.findRecent(date).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<Shift> findRecent(Date date, ShiftOrder order) {
		 return shiftIdRepo.findRecent(date,order).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<Shift> findLast(Integer num) {
		 return shiftIdRepo.findLast(num).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<Shift> findFromTo(Integer from,Integer to) {
		 return shiftIdRepo.findFromTo(from,to).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<Shift> findBetween(Date older,Date newer) {
		 return shiftIdRepo.findBetween(older,newer).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<Shift> findBetween(Date older, Date newer, ShiftOrder order) {
		 return shiftIdRepo.findBetween(older,newer,order).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<Pushable> saveAll(List<Shift> shifts) {
		List<Pushable> result = new ArrayList<>();
		for (Shift shift : shifts) {
			result.addAll(save(shift));
		}
		return result;
	}

	public List<Pushable> save(Shift s) {
		List<Pushable> result = s.isPushable();
		Optional<UUID> ouuid = shiftIdRepo.save(s.getShiftId());
		if(ouuid.isPresent() && result.isEmpty()){
			UUID uuid = ouuid.get();
			s.getShiftId().setId(uuid);
			if(result.isEmpty()) {
					if(s.getProblems() != null) {
						s.getProblems().forEach(p -> {p.setShiftId(uuid); problemDetailsRepo.save(p);});
					}
					s.getEmployees().forEach(e -> employeeRepo.saveToShift(e.getId(),s.getShiftId().getId()));
					s.getTotalFlowAverage().forEach(t -> {t.setShiftId(uuid); totalFlowRepo.save(t);});
					s.getTemps().forEach(t -> {t.setShiftId(uuid); temperatureRepo.save(t);});
					if(s.getNotes() != null) {
						s.getNotes().forEach(n -> {n.setId(uuid); noteRepo.save(n);});
					}
			}
		}
		return result;
	}
	
	public Shift fullFill(ShiftId shiftId) {
		UUID id = shiftId.getId();
		return new Shift(shiftId,
				problemDetailsRepo.findByShiftId(id),
				employeeRepo.findByShiftId(id),
				totalFlowRepo.findByShiftId(id),
				temperatureRepo.findByShiftId(id),
				noteRepo.findByShiftId(id));
	}
}
