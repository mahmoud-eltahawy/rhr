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
public class ProblemDetail {
	private UUID id;
	private List<Problem> problems;
	private Machine machine;
	private Time beginTime;
	private Time endTime;
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(machine != null) {
			canPush.addAll(machine.isPushable());
		} else {
			canPush.add(Pushable.PROBLEM_DETAIL_MACHINE_IS_NULL);
		}
		if(problems != null) {
			for (Problem problem : problems) {
				canPush.addAll(problem.isPushable());
			}
			if(problems.size() == 0) {
				canPush.add(Pushable.PROBLEM_DETAIL_PROBLEMS_LIST_IS_EMPTY);
			}
		} else {
			canPush.add(Pushable.PROBLEM_DETAIL_PROBLEMS_IS_NULL);
		}
		if(beginTime == null) {
			canPush.add(Pushable.PROBLEM_DETAIL_BEGIN_TIME_IS_NULL);
		}
		if(endTime == null) {
			canPush.add(Pushable.PROBLEM_DETAIL_END_TIME_IS_NULL);
		}
		if(beginTime != null && endTime != null) {
			if(endTime.before(beginTime)) {
				canPush.add(Pushable.PROBLEM_DETAIL_END_TIME_IS_BEFORE_BEGIN_TIME);
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
		ProblemDetail other = (ProblemDetail) obj;
		return GF.equals(beginTime, other.beginTime) &&
				GF.equals(endTime, other.endTime)
				&& machine.equals(other.machine) && Objects.equals(problems, other.problems);
	}
	@Override
	public int hashCode() {
		return Objects.hash(beginTime, endTime, machine, problems);
	}
}
