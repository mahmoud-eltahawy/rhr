package com.rhr.heat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.rhr.heat.enums.Pushable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Machine extends Identity {
	private String category;
	private Integer number;

	public Machine(UUID id, String category, Integer number) {
		super(id);
		this.category = category;
		this.number = number;
	}

	public Machine(UUID id) {
		super(id);
	}
	
	public String name() {
		return category+" "+number;
	}
	
	public List<Pushable> isPushable() {
		List<Pushable> canPush = new ArrayList<>();
		if(id == null) {
			canPush.add(Pushable.MACHINE_id_NULL);
		}
		if(category == null) {
			canPush.add(Pushable.MACHINE_CATEGORY_NULL);
		}
		if(number == null) {
			canPush.add(Pushable.MACHINE_NUMBER_NULL);
		}
		return canPush;
	}
}