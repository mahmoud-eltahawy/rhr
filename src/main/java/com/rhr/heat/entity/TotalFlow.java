package com.rhr.heat.entity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.rhr.heat.enums.Pushable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TotalFlow extends Identity {
	private List<Machine> suspendedMachines;
	private Integer minFlow;
	private Integer maxFlow;
	private Time caseBeginTime;
	private Time caseEndTime;

	public TotalFlow(UUID id, List<Machine> suspendedMachines, Integer minFlow, Integer maxFlow, Time caseBeginTime,
			Time caseEndTime) {
		super(id);
		this.suspendedMachines = suspendedMachines;
		this.minFlow = minFlow;
		this.maxFlow = maxFlow;
		this.caseBeginTime = caseBeginTime;
		this.caseEndTime = caseEndTime;
	}
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(suspendedMachines != null) {
			if(suspendedMachines.size() != 0) {
				for(Machine m : suspendedMachines) {
					canPush.addAll(m.isPushable());
				}
			} else {
				canPush.add(Pushable.TOTAL_FLOW_MACHINE_LIST_IS_EMPTY);
			}
		} else {
			canPush.add(Pushable.TOTAL_FLOW_MACHINE_LIST_IS_NULL);
		}
		if(minFlow == null) {
			canPush.add(Pushable.TOTAL_FLOW_MIN_IS_NULL);
		}
		if(maxFlow == null) {
			canPush.add(Pushable.TOTAL_FLOW_MAX_IS_NULL);
		}
		if(caseBeginTime == null) {
			canPush.add(Pushable.TOTAL_FLOW_BEGIN_TIME_IS_NULL);
		}
		if(caseEndTime == null) {
			canPush.add(Pushable.TOTAL_FLOW_END_TIME_IS_NULL);
		}
		if(maxFlow != null && minFlow != null) {
			if(maxFlow > minFlow) {
				canPush.add(Pushable.TOTAL_FLOW_MAX_LESS_THAN_MIN);
			}
		}
		return canPush;
	}
}