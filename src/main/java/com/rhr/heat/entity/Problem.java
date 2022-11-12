package com.rhr.heat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem {
	private String title;
	private String description;
	
	public Boolean isPushable() {
		if(title != null && description != null) {
			return true;
		}
		return false;
	}
}