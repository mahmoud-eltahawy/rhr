package com.rhr.heat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.rhr.heat.enums.Pushable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Machine {
	private UUID id;
	private String category;
	private Integer number;

	public Machine(String category, Integer number) {
		this.category = category;
		this.number = number;
	}
	
	public String name() {
		if(number != 0) {
			return category+"-"+number;
		} else {
			return category;
		}
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

	@Override
	public int hashCode() {
		return Objects.hash(category, number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Machine other = (Machine) obj;
		return Objects.equals(category, other.category) && Objects.equals(number, other.number);
	}
}