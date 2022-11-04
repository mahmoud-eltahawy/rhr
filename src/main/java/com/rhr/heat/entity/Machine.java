package com.rhr.heat.entity;

import java.util.Objects;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Machine {
	private UUID id;
	private String catagory;
	private Integer number;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Machine other = (Machine) obj;
		return Objects.equals(catagory, other.catagory) && Objects.equals(number, other.number);
	}
	@Override
	public int hashCode() {
		return Objects.hash(catagory, number);
	}
}