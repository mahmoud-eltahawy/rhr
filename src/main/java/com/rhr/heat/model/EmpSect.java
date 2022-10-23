package com.rhr.heat.model;

import java.util.ArrayList;
import java.util.List;

import com.rhr.heat.entity.ShiftId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpSect {
	private ShiftId one;
	private ShiftId two;
	private ShiftId three;
	private ShiftId four;
	private ShiftId five;
	
	public static List<EmpSect> days(List<ShiftId> ids){
		List<EmpSect> days = new ArrayList<EmpSect>();
		for(int i = 0; i < ids.size(); i++) {
			if(i % 5 == 0) {
				EmpSect es = new EmpSect();
				es.setOne(ids.get(i));
				days.add(es);
			} else if(i % 5 == 1) {
				EmpSect es = days.get(days.size() - 1);
				es.setTwo(ids.get(i));
				days.set(days.size() - 1, es);
			} else if(i % 5 == 2) {
				EmpSect es = days.get(days.size() - 1);
				es.setThree(ids.get(i));
				days.set(days.size() - 1, es);
			} else if(i % 5 == 3) {
				EmpSect es = days.get(days.size() - 1);
				es.setFour(ids.get(i));
				days.set(days.size() - 1, es);
			} else if(i % 5 == 4) {
				EmpSect es = days.get(days.size() - 1);
				es.setFive(ids.get(i));
				days.set(days.size() - 1, es);
			}
		}
		return days;
	}
}
