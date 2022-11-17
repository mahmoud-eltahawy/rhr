package com.rhr.heat.entity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.rhr.heat.enums.Pushable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalFlow extends Identity {
	private UUID shift_id;
	private List<Machine> suspendedMachines;
	private Integer minFlow;
	private Integer maxFlow;
	private Time caseBeginTime;
	private Time caseEndTime;

	public TotalFlow(UUID id, UUID shift_id, List<Machine> suspendedMachines,
			Integer minFlow, Integer maxFlow,Time caseBeginTime, Time caseEndTime) {
		super(id);
		this.shift_id = shift_id;
		this.suspendedMachines = suspendedMachines;
		this.minFlow = minFlow;
		this.maxFlow = maxFlow;
		this.caseBeginTime = caseBeginTime;
		this.caseEndTime = caseEndTime;
	}
	
	public TotalFlow(UUID id) {
		super(id);
	}
	
	@Override
	public Boolean isSameAs(Identity identity) {
		TotalFlow other = (TotalFlow) identity;
		if(caseBeginTime.equals(other.caseBeginTime)&& 
				caseEndTime.equals(other.caseEndTime)) {
			return true;
		}
		return false;
	}
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(id == null) {
			canPush.add(Pushable.TOTAL_FLOW_ID_IS_NULL);
		}
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
			if(maxFlow <= minFlow) {
				canPush.add(Pushable.TOTAL_FLOW_MAX_LESS_THAN_MIN);
			}
		}
		if(caseBeginTime != null && caseEndTime != null) {
			if(caseEndTime.before(caseBeginTime)) {
				canPush.add(Pushable.TOTAL_FLOW_END_TIME_IS_BEFORE_BEGIN_TIME);
			}
		}
		return canPush;
	}
}