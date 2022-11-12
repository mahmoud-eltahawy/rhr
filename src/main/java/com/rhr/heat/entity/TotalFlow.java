package com.rhr.heat.entity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.rhr.heat.GF;
import com.rhr.heat.enums.Pushable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalFlow {
	private UUID id;
	private List<Machine> suspendedMachines;
	private Integer minFlow;
	private Integer maxFlow;
	private Time caseBeginTime;
	private Time caseEndTime;
	
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
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TotalFlow other = (TotalFlow) obj;
		return GF.equals(caseBeginTime, other.caseBeginTime) &&
				GF.equals(caseEndTime, other.caseEndTime);
	}
	@Override
	public int hashCode() {
		return Objects.hash(caseBeginTime, caseEndTime);
	}
}