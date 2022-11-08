package com.rhr.heat.dao.topLayer;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.rhr.heat.dao.EmployeeRepo;
import com.rhr.heat.dao.NoteRepo;
import com.rhr.heat.dao.ShiftIdRepo;
import com.rhr.heat.dao.TemperatureRepo;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.entity.topLayer.MiniShift;
import com.rhr.heat.enums.ShiftOrder;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MiniShiftRepo {
	private final ShiftIdRepo shiftIdRepo;
	private final EmployeeRepo employeeRepo;
	private final TemperatureRepo temperatureRepo;
	private final NoteRepo noteRepo;

	public List<MiniShift> findAll() {
		 return shiftIdRepo.findAll().stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}
	
	public List<MiniShift> findAll(Date date) {
		 return shiftIdRepo.findAll(date).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<MiniShift> findAll(ShiftOrder order) {
		 return shiftIdRepo.findAll(order).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public Optional<MiniShift> findById(UUID uuid) {
		 Optional<ShiftId> shiftId = shiftIdRepo.findById(uuid);
		 if(shiftId.isPresent()) {
			return Optional.of(fullFill(shiftId.get())); 
		 } else {
			return Optional.of(null); 
		 }
	}

	public Optional<MiniShift> findById(Date date, ShiftOrder order) {
		 Optional<ShiftId> shiftId = shiftIdRepo.findById(date,order);
		 if(shiftId.isPresent()) {
			return Optional.of(fullFill(shiftId.get())); 
		 } else {
			return Optional.of(null); 
		 }
	}

	public List<MiniShift> findOlderThan(Date date) {
		 return shiftIdRepo.findOlderThan(date).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<MiniShift> findOlderThan(Date date,ShiftOrder order) {
		 return shiftIdRepo.findOlderThan(date,order).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<MiniShift> findRecent(Date date) {
		 return shiftIdRepo.findRecent(date).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<MiniShift> findRecent(Date date, ShiftOrder order) {
		 return shiftIdRepo.findRecent(date,order).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<MiniShift> findLast(Integer num) {
		 return shiftIdRepo.findLast(num).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<MiniShift> findFromTo(Integer from,Integer to) {
		 return shiftIdRepo.findFromTo(from,to).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<MiniShift> findBetween(Date older,Date newer) {
		 return shiftIdRepo.findBetween(older,newer).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}

	public List<MiniShift> findBetween(Date older, Date newer, ShiftOrder order) {
		 return shiftIdRepo.findBetween(older,newer,order).stream()
				 .map(id -> fullFill(id))
				 .collect(Collectors.toList());
	}
	
	private MiniShift fullFill(ShiftId shiftId) {
		UUID id = shiftId.getId();
		return new MiniShift(shiftId,
				employeeRepo.findByShiftId(id),
				temperatureRepo.findByShiftId(id),
				noteRepo.findByShiftId(id));
	}
}
