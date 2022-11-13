package com.rhr.heat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.rhr.heat.enums.Pushable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Machine {
	private UUID id;
	private String category;
	private Integer number;
	
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
	@Override
	public int hashCode() {
		return Objects.hash(category, number);
	}
}