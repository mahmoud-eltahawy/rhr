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
public class ProblemDetail extends Identity {
	private UUID shiftId;
	private List<Problem> problems;
	private Machine machine;
	private Time beginTime;
	private Time endTime;

	public ProblemDetail(Category category,Integer number){
		this.machine = new Machine(category, number);
	}

	public ProblemDetail(UUID id, UUID shiftId, List<Problem> problems,
			Machine machine, Time beginTime, Time endTime) {
		super(id);
		this.shiftId = shiftId;
		this.problems = problems;
		this.machine = machine;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	public ProblemDetail(UUID id) {
		super(id);
	}

	@Override
	public Boolean isSameAs(Identity identity) {
		ProblemDetail other = (ProblemDetail) identity;
		if(problems.equals(other.problems) &&
				machine.equals(other.machine) &&
						beginTime.equals(other.beginTime) && 
								endTime.equals(other.endTime)) {
			return true;
		}
		return false;
	}
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(id == null) {
			canPush.add(Pushable.PROBLEM_DETAIL_ID_IS_NULL);
		}
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
