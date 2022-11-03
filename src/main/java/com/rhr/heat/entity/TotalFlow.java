package com.rhr.heat.entity;

import java.sql.Time;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.rhr.heat.Tools;

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
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TotalFlow other = (TotalFlow) obj;
		return Tools.equals(caseBeginTime, other.caseBeginTime) &&
				Tools.equals(caseEndTime, other.caseEndTime);
	}
	@Override
	public int hashCode() {
		return Objects.hash(caseBeginTime, caseEndTime);
	}
}