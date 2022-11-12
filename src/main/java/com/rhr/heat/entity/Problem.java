package com.rhr.heat.entity;

import java.util.ArrayList;
import java.util.List;

import com.rhr.heat.enums.Pushable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem {
	private String title;
	private String description;
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(title == null) {
			canPush.add(Pushable.PROBLEM_TITLE_IS_NULL);
		}
		if(description == null) {
			canPush.add(Pushable.PROBLEM_DESCRIPTION_IS_NULL);
		}
		return canPush;
	}
}