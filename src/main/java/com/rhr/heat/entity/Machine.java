package com.rhr.heat.entity;

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
public class Machine extends Identity {
	private String category;
	private Integer number;

	public Machine(UUID id, String category, Integer number) {
		super(id);
		this.category = category;
		this.number = number;
	}
	
	public String name() {
		return category+" "+number;
	}
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(category == null) {
			canPush.add(Pushable.MACHINE_CATEGORY_NULL);
		}
		if(number == null) {
			canPush.add(Pushable.MACHINE_NUMBER_NULL);
		}
		return canPush;
	}
}