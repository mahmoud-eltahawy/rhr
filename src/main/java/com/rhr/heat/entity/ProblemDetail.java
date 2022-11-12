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
public class ProblemDetail {
	private UUID id;
	private List<Problem> problems;
	private Machine machine;
	private Time beginTime;
	private Time endTime;
	
	public Boolean isPushable() {
		if(machine.isPushable() && beginTime != null && endTime != null && problems != null) {
			if(problems.size() > 0 && endTime.after(beginTime)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProblemDetail other = (ProblemDetail) obj;
		return Tools.equals(beginTime, other.beginTime) &&
				Tools.equals(endTime, other.endTime)
				&& machine.equals(other.machine) && Objects.equals(problems, other.problems);
	}
	@Override
	public int hashCode() {
		return Objects.hash(beginTime, endTime, machine, problems);
	}
}
