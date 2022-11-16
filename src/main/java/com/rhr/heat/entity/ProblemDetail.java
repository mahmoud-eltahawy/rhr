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
public class ProblemDetail extends Identity {
	private List<Problem> problems;
	private Machine machine;
	private Time beginTime;
	private Time endTime;

	public ProblemDetail(UUID id, List<Problem> problems, Machine machine, Time beginTime, Time endTime) {
		super(id);
		this.problems = problems;
		this.machine = machine;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}
	
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
}
