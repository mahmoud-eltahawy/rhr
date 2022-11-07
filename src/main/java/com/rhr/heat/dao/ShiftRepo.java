package com.rhr.heat.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.rhr.heat.entity.Shift;
import com.rhr.heat.entity.ShiftId;
import com.rhr.heat.enums.ShiftOrder;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShiftRepo {
	private final EmployeeRepo employeeRepo;
	private final ProblemDetailsRepo problemDetailsRepo;
	private final ShiftIdRepo shiftIdRepo;
	private final TotalFlowRepo totalFlowRepo;

	public List<Shift> findAll() {
		 return null;
	}
	
	public List<Shift> findAll(Date date) {
		 return null;
	}

	public List<Shift> findAll(ShiftOrder order) {
		 return null;
	}

	public Optional<Shift> findById(UUID id) {
			 return null;
	}

	public Optional<Shift> findById(Date date, ShiftOrder order) {
		return null;
	}

	public List<Shift> findOlderThan(Date date) {
		 return null;
	}

	public List<Shift> findOlderThan(Date date,ShiftOrder order) {
		 return null;
	}

	public List<Shift> findRecent(Date date) {
		 return null;
	}

	public List<Shift> findRecent(Date date, ShiftOrder order) {
		 return null;
	}

	public List<Shift> findLast(Integer num) {
		 return null;
	}

	public List<Shift> findFromTo(Integer from,Integer to) {
		 return null;
	}

	public List<Shift> findBetween(Date older,Date newer) {
		 return null;
	}

	public List<Shift> findBetween(Date older, Date newer, ShiftOrder order) {
		 return null;
	}

	public List<UUID> saveAll(List<Shift> shifts) {
		return shifts.stream()
				.map(s -> {return save(s);})
				.collect(Collectors.toList());
	}

	public UUID save(Shift s) {
		UUID theId = shiftIdRepo.save(s.getShiftId());
		if(theId == null) {
			return theId;
		} else {
			s.getProblems().forEach(p -> problemDetailsRepo.saveToShift(p, theId));
			s.getEmployees().forEach(e -> employeeRepo.saveToShift(e, theId));
			s.getTotalFlowAverage().forEach(t -> totalFlowRepo.saveToShift(t, theId));
			return theId;
		}
	}
	
	private Shift fullFill(ShiftId id) {
		Shift shift = new Shift();
		shift.setShiftId(id);
		shift.setEmployees(employeeRepo.findInShift(id.getId()));
		shift.setProblems(problemDetailsRepo.findInShift(id.getId()));
		shift.setTotalFlowAverage(totalFlowRepo.findInShift(id.getId()));
		 return shift;
	}
}
