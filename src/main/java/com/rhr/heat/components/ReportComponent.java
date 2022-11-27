package com.rhr.heat.components;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.rhr.heat.dao.MachineRepo;
import com.rhr.heat.dao.ShiftIdRepo;
import com.rhr.heat.deep.service.ShiftTimer;
import com.rhr.heat.entity.Machine;
import com.rhr.heat.entity.ShiftId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReportComponent {
	private final ShiftTimer timer;
	private final ShiftIdRepo shiftIdRepo;
	private final MachineRepo machineRepo;
    
	public Optional<Machine> parseMachine(String sm) {
		String[] arr = sm.split("-");
		return machineRepo.findByTheId(arr[0], Integer.parseInt(arr[1]));
	}

	public ShiftId getCurrentShift() {
		ShiftId newId = timer.currentShiftId();
		Optional<ShiftId> oldId = shiftIdRepo
			.findById(newId.getDate(), newId.getShift());
		if(oldId.isPresent()){
			return oldId.get();
		} else {
			Optional<UUID> uuid = shiftIdRepo.save(newId);
			if(uuid.isPresent()){
				newId.setId(uuid.get());
				return newId;
			} else {
				return null;
			}
		}
	}
}
